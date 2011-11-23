#include <jni.h>
#include <jvmti.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <stddef.h>
#include <stdarg.h>

#include "HeapJVMTI.h"

#define MAX_FRAMES 20

typedef struct Trace {
    /* Number of frames (includes HEAP_TRACKER methods) */
    jint           nframes;
    /* Frames from GetStackTrace() (2 extra for HEAP_TRACKER methods) */
    jvmtiFrameInfo frames[MAX_FRAMES+2];

} Trace;

typedef struct {

	//Flag used to count an object only once
	int last_mark;

	//Pointer to the object
	jobject *object_ptr;

	/* Trace where this object was allocated from */
	Trace	trace;

	int timestamp;

	int length;

} TraceInfo;

typedef struct {

	int reachable_from_root;
	int reachable_from_stack;
	int reachable_from_ref;

	int fresh;

	//La suma de los reachable no tiene que dar necesariamente  reachable_from_root + reachable_from_stack + reachable_from_ref
	int reachable_objects;

	int sequence;

	int from_timestamp;

	int to_timestamp;

} HeapInfo;

//Environment
//FIXME Def a structure to keep this information
static jvmtiEnv  *jvmti;
static jrawMonitorID lock;
static int last_mark = 0;

/*
static int switchMark() {
	mark = mark == 0 ? 1 :0;
	return mark;
}

static void updateMark(TraceInfo *tinfo) {
	tinfo->mark = tinfo->mark == 0?  1 : 0;
}*/

static int mark(TraceInfo *tinfo, long sequence) {
	if(tinfo->last_mark < sequence) {
		tinfo->last_mark = sequence;
		return 1;
	}
	return 0;
}

void stdout_message(const char * format, ...) {
    va_list ap;
    va_start(ap, format);
    (void)vfprintf(stdout, format, ap);
    va_end(ap);
}

void fatal_error(const char * format, ...) {
    va_list ap;

    va_start(ap, format);
    (void)vfprintf(stderr, format, ap);
    (void)fflush(stderr);
    va_end(ap);
    exit(3);
}

void check_jvmti_error(jvmtiEnv *jvmti, jvmtiError errnum, const char *str) {
    if ( errnum != JVMTI_ERROR_NONE ) {
	char *errnum_str;
	
	errnum_str = NULL;
	jvmti->GetErrorName(errnum, &errnum_str);
	
	fatal_error("ERROR: JVMTI: %d(%s): %s\n", errnum, 
		(errnum_str==NULL?"Unknown":errnum_str),
		(str==NULL?"":str));
    }
}

/* All memory allocated by JVMTI must be freed by the JVMTI Deallocate
 *   interface.
 */
void deallocate(jvmtiEnv *jvmti, void* ptr){
    jvmtiError error;

    error = jvmti->Deallocate((unsigned char*)ptr);
    check_jvmti_error(jvmti, error, "Cannot deallocate memory");
}


JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM *vm, char *options, void *reserved) {
    
    jvmtiError             error;
    jint                   res;
    jvmtiCapabilities      capabilities;
    jvmtiEventCallbacks    callbacks;

    // Create the JVM TI environment (jvmti).
    res = vm->GetEnv((void **)&jvmti, JVMTI_VERSION_1);
    
   
    
    /* Get/Add JVMTI capabilities */ 
    (void)memset(&capabilities, 0, sizeof(capabilities));
    capabilities.can_tag_objects = 1;
    error = jvmti->AddCapabilities(&capabilities);
    check_jvmti_error(jvmti, error, "Cannot add capabilities");
   
    // Clear the callbacks structure and set the ones you want.
  /*  (void)memset(&callbacks,0, sizeof(callbacks));
    callbacks.VMStart           = &cbVMStart;
    callbacks.VMInit            = &cbVMInit;
    callbacks.VMDeath           = &cbVMDeath;
    callbacks.ObjectFree        = &cbObjectFree;
    callbacks.VMObjectAlloc     = &cbVMObjectAlloc;
    callbacks.ClassFileLoadHook = &cbClassFileLoadHook;
    error = (*jvmti)->SetEventCallbacks(jvmti, &callbacks, 
                      (jint)sizeof(callbacks));
		      */
    //  If error!=JVMTI_ERROR_NONE, the callbacks were not accepted.

    // For each of the above callbacks, enable this event.
    /*
    error = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                      JVMTI_EVENT_VM_START, (jthread)NULL);
    error = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                      JVMTI_EVENT_VM_INIT, (jthread)NULL);
    error = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                      JVMTI_EVENT_VM_DEATH, (jthread)NULL);
    error = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                      JVMTI_EVENT_OBJECT_FREE, (jthread)NULL);
    error = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                      JVMTI_EVENT_VM_OBJECT_ALLOC, (jthread)NULL);
    error = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE,
                      JVMTI_EVENT_CLASS_FILE_LOAD_HOOK, 
                      (jthread)NULL);
		      */
    // In all the above calls, check errors.

    // Create a raw monitor in the agent for critical sections.
   //FIXME error = (*jvmti)->CreateRawMonitor(jvmti, "agent data", 
        //              &(agent_lock));
    // If error!=JVMTI_ERROR_NONE, then you haven't got a lock!

    return JNI_OK; // Indicates to the VM that the agent loaded OK.
}

static TraceInfo * newTraceInfo() {
	TraceInfo *tinfo;
	tinfo = (TraceInfo*)calloc(1, sizeof(TraceInfo));
	tinfo->last_mark = -1;
	tinfo->object_ptr = NULL;
	return tinfo;
}

static void printFrame(jvmtiFrameInfo *finfo) {

		jclass	klass;
	    char	*signature;
	    char	*methodname;
	    char	*methodsig;
	    jvmtiError	error;

	    /* Get jclass object for the jmethodID */
		error = jvmti->GetMethodDeclaringClass(finfo->method, &klass);
	    check_jvmti_error(jvmti, error, "Cannot get method's class");

	    /* Get the class signature */
	    error = jvmti->GetClassSignature(klass, &signature, NULL);
	    check_jvmti_error(jvmti, error, "Cannot get class signature");

	    /* Skip all this if it's our own Tracker method
	    if ( strcmp(signature, "L" STRING(HEAP_TRACKER_class) ";" ) == 0 ) {
	        deallocate(jvmti, signature);
	        return;
	    }
*/
	    /* Get the name and signature for the method */
	    error = jvmti->GetMethodName(finfo->method,&methodname, &methodsig, NULL);
	    check_jvmti_error(jvmti, error, "Cannot get method name");

	    printf("%s \n", methodname);

	    deallocate(jvmti, signature);
	    deallocate(jvmti, methodsig);
	    deallocate(jvmti, methodname);

}



JNIEXPORT void JNICALL Java_ar_uba_dc_tools_instrumentation_resource_tracker_runtime_HeapJVMTI_setTag(JNIEnv * env, jobject o1,
		jobject object, jlong timestamp, jint length) {
  
	TraceInfo *tinfo = newTraceInfo();
	tinfo->object_ptr = &object;
	tinfo->timestamp = timestamp;
	tinfo->length = length;
	jlong tinfoTag = (jlong)(ptrdiff_t)(void*)tinfo;


	jvmtiError error = jvmti->SetTag(object, tinfoTag);
	check_jvmti_error(jvmti, error, "Cannot execute SetTag" );

}

static int is_fresh(TraceInfo* tinfo, HeapInfo* hinfo) {
	int fresh = 1;

	if(hinfo->from_timestamp != -1) {
		fresh = tinfo->timestamp > hinfo->from_timestamp;
	//	printf("from_timestamp %d , parameter %d, included? %d \n", hinfo->from_timestamp, tinfo->timestamp, fresh);

	}

	if(hinfo->to_timestamp != -1) {
		fresh = fresh && (tinfo->timestamp <= hinfo->to_timestamp);
	//	printf("to_timestamp %d , parameter %d, included? %d \n", hinfo->to_timestamp, tinfo->timestamp, fresh);
	}

	return fresh;

}
static jvmtiIterationControl JNICALL heap_root_callback(jvmtiHeapRootKind root_kind, 
    jlong class_tag, jlong size, jlong* tag_ptr, void* user_data) {
      
	 HeapInfo *heapInfo = (HeapInfo *)user_data;
	 if(*tag_ptr != 0) {
		  TraceInfo *tinfo = (TraceInfo*)(ptrdiff_t)(*tag_ptr);
		  heapInfo->reachable_from_root++;

		//  printf("root callback for %d\n", tinfo->timestamp);

		  //Si no fue previamente visitado
		  int include = mark(tinfo, heapInfo->sequence);

		  //Buscamos que sea fresh
		  if(heapInfo->fresh)
			  include = include && is_fresh(tinfo, heapInfo);

		  if(include) {
			heapInfo->reachable_objects+= ((tinfo->length != -1)? tinfo->length : 1);
		//	printf("Object reachable %d\n", tinfo->timestamp);
		  }
	  }
	  return JVMTI_ITERATION_CONTINUE;
}


static jvmtiIterationControl JNICALL stack_reference_callback(jvmtiHeapRootKind root_kind, 
	jlong class_tag, jlong size, jlong* tag_ptr, jlong thread_tag, jint depth, jmethodID method, jint slot, void* user_data) {

	 HeapInfo *heapInfo = (HeapInfo *)user_data;

      if(*tag_ptr != 0) {
    	  TraceInfo *tinfo = (TraceInfo*)(ptrdiff_t)(*tag_ptr);
    	//  printf("stack callback for %d\n", tinfo->timestamp);
    	  heapInfo->reachable_from_stack++;
    	  //Si no fue previamente visitado
    	  int include = mark(tinfo, heapInfo->sequence);
		  if(heapInfo->fresh)
			  include = include && is_fresh(tinfo, heapInfo);

		  if(include) {
			  heapInfo->reachable_objects+= ((tinfo->length != -1)? tinfo->length : 1);
		//	printf("Object reachable by stack ref %d\n", tinfo->timestamp);
		  }
      }
      return JVMTI_ITERATION_CONTINUE;
}

static jvmtiIterationControl JNICALL heap_reference_callback(jvmtiObjectReferenceKind reference_kind,
		jlong class_tag, jlong size, jlong* tag_ptr, jlong referrer_tag, jint referrer_index, void* user_data) {

	 HeapInfo *heapInfo = (HeapInfo *)user_data;
	 if(*tag_ptr != 0) {
		  TraceInfo *tinfo = (TraceInfo*)(ptrdiff_t)(*tag_ptr);
		  heapInfo->reachable_from_ref++;

		//  printf("heap ref callback for %d\n", tinfo->timestamp);
		  //Si no fue previamente visitado
		  int include = mark(tinfo, heapInfo->sequence);
		  		  if(heapInfo->fresh)
		  			  include = include && is_fresh(tinfo, heapInfo);

		  if(include) {
			  heapInfo->reachable_objects+= ((tinfo->length != -1)? tinfo->length : 1);
	//		printf("Object reachable by heap ref %d\n", tinfo->timestamp);
		  }


	  }
	  return JVMTI_ITERATION_CONTINUE;
}


JNIEXPORT jint JNICALL Java_ar_uba_dc_tools_instrumentation_resource_tracker_runtime_HeapJVMTI_countReachable
  (JNIEnv *env, jobject object, jlong from_timestamp, jlong to_timestamp) {
    
    jvmtiError error;

    HeapInfo heapInfo;
    heapInfo.sequence = last_mark++;
    heapInfo.reachable_from_ref = 0;
    heapInfo.reachable_from_root = 0;
    heapInfo.reachable_from_stack = 0;
    heapInfo.reachable_objects = 0;
    heapInfo.fresh = 1;
    heapInfo.from_timestamp = from_timestamp;
    heapInfo.to_timestamp = to_timestamp;

//  error = jvmti->GetStackTrace((jthread)NULL, 0, MAX_FRAMES+2,heapInfo.trace.frames, &heapInfo.trace.nframes);
// 	check_jvmti_error(jvmti, error, "Error detected while gettint the stack trace");


    error = jvmti->IterateOverReachableObjects(&heap_root_callback, &stack_reference_callback, &heap_reference_callback, (void *)&heapInfo);
    check_jvmti_error(jvmti, error, "Cannot execute IterateOverReachableObjects" );
    
    return heapInfo.reachable_objects;
}

/* Callback for JVMTI_EVENT_OBJECT_FREE */
void JNICALL ObjectFree(jvmtiEnv *jvmti_env, jlong tag) {

	 stdout_message("ObjectFree\n");

	TraceInfo *tinfo;

    /* The object tag is actually a pointer to a TraceInfo structure */
    tinfo = (TraceInfo*)(void*)(ptrdiff_t)tag;
    //free tinfo FIXME
}
