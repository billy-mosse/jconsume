package ar.uba.dc.daikon;


public class Ins20 {
    public Ins20() {
    }

    public static void main(String[] var0) {
        String[] var1 = null;
        var1 = var0;
        String var10000 = var0[0];
        //InstrumentedMethod.ar_uba_dc_daikon_Ins19_CC_00019(var0[0], var0);
        int var2 = Integer.parseInt(var10000);
        //InstrumentedMethod.ar_uba_dc_daikon_Ins19_CS_00021(var2, var1);
        RichNumberPublic var3 = new RichNumberPublic(var2);
        //InstrumentedMethod.ar_uba_dc_daikon_Ins19_CC_00023(var1);
        mainParameters(var3);
    }

    public static void mainParameters(RichNumberPublic var0) {
        Object var1 = null;
        int var3 = var0.number;

        for(int var2 = 1; var2 < var3; ++var2) {
            //InstrumentedMethod.ar_uba_dc_daikon_Ins19_CC_00032(var3, var2);
           // ar.uba.dc.daikon.//InstrumentedMethod.a32(var0, var2);
            //InstrumentedMethod.ar_uba_dc_daikon_Ins19_CC_00033(var3, var2);
           // ar.uba.dc.daikon.//InstrumentedMethod.a33(var3, var2);
            //InstrumentedMethod.ar_uba_dc_daikon_Ins19_CS_00034(var3, var2);
            new Integer(var2);
        }

    }
}