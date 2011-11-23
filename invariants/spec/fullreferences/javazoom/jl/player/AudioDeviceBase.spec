<?xml version="1.0" encoding="UTF-8"?>
<spec>
    <class decl="javazoom.jl.player.AudioDeviceBase">
    	<method decl="void write(short[],int,int)">
       		<relevant-parameters>len</relevant-parameters>
        	<requires>len > 0</requires>
	     	<call-site offset="1">
	        	<constraints>$t.len == len and $t.thisBufLen == len</constraints>
	      	</call-site>
	 	</method>
	</class>
</spec>