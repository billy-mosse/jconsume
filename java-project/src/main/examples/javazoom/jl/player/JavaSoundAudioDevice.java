/*
 * 11/26/04		Buffer size modified to support JRE 1.5 optimizations.
 *              (CPU usage < 1% under P4/2Ghz, RAM < 12MB).
 *              jlayer@javazoom.net
 * 11/19/04		1.0 moved to LGPL.
 * 06/04/01		Too fast playback fixed. mdm@techie.com
 * 29/01/00		Initial version. mdm@techie.com
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package javazoom.jl.player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

/*import com.sun.media.sound.MixerSourceLine;
import com.sun.org.apache.xerces.internal.impl.dtd.models.MixedContentModel;*/

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;


/**
 * The <code>JavaSoundAudioDevice</code> implements an audio
 * device by using the JavaSound API.
 *
 * @since 0.0.8
 * @author Mat McGowan
 */
public class JavaSoundAudioDevice extends AudioDeviceBase
{
	private SourceDataLine	source = null;

	private AudioFormat		fmt = null;

	private byte[]			byteBuf = new byte[4096]; //Residual 4096

	/**
	 * Residual = 0
	 * Temporal = 0
	 * @param fmt0
	 */
	protected void setAudioFormat(AudioFormat fmt0) {
		fmt = fmt0;
	}

	
	/**
	 * 1 + Res(AudioFormat.init<>)
	 * @return
	 */
	protected AudioFormat getAudioFormat() {
		if (fmt==null) 	{
			Decoder decoder = getDecoder();
			fmt = new AudioFormat(decoder.getOutputFrequency(),
								  16,
								  decoder.getOutputChannels(),
								  true,
								  false);
		}
		return fmt;
	}

	protected DataLine.Info getSourceLineInfo() {
		AudioFormat fmt = getAudioFormat();
		//DataLine.Info info = new DataLine.Info(SourceDataLine.class, fmt, 4000);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, fmt);
		return info;
	}

	/**
	 * Temporal = 0
	 * Residual = 0
	 * @param fmt
	 * @throws JavaLayerException
	 */
	public void open(AudioFormat fmt) throws JavaLayerException {
		
		if (!isOpen()) {
			setAudioFormat(fmt);
			openImpl();
			setOpen(true);
		}
	}

	/* Temporal = 0
	 * Residual = 0
	 * (non-Javadoc)
	 * @see javazoom.jl.player.AudioDeviceBase#openImpl()
	 */
	protected void openImpl() throws JavaLayerException { }
	Line getLine(Info sli) throws LineUnavailableException
	{
//		HeadspaceMixerProvider mp = new HeadspaceMixerProvider();
//		Mixer m = mp.getMixer(mp.getMixerInfo()[0]);
//		return m.getLine(sli);
		return new FakeSourceLine();
	}


	// createSource fix.
	protected void createSource() throws JavaLayerException
    {
        Throwable t = null;
        try
        {
        	Line line = getLine(getSourceLineInfo());
			//Line line = AudioSystem.getLine(getSourceLineInfo());
            if (line instanceof SourceDataLine)
            {
         		source = (SourceDataLine)line;
                //source.open(fmt, millisecondsToBytes(fmt, 2000));
				source.open(fmt);
                /*
                if (source.isControlSupported(FloatControl.Type.MASTER_GAIN))
                {
					FloatControl c = (FloatControl)source.getControl(FloatControl.Type.MASTER_GAIN);
                    c.setValue(c.getMaximum());
                }*/
                source.start();

            }
        } catch (RuntimeException ex)
          {
			  t = ex;
          }
          catch (LinkageError ex)
          {
              t = ex;
          }
          catch (LineUnavailableException ex)
          {
              t = ex;
          }
		if (source==null) throw new JavaLayerException("cannot obtain source audio line", t);
    }

	
	/** Temporal = 0
	 *  Residual = 0
	 * @param fmt
	 * @param time
	 * @return
	 */
	public int millisecondsToBytes(AudioFormat fmt, int time) {
		return (int)(time*(fmt.getSampleRate()*fmt.getChannels()*fmt.getSampleSizeInBits())/8000.0);
	}

	/* Residual = 0
	 * Temporal = 0
	 * (non-Javadoc)
	 * @see javazoom.jl.player.AudioDeviceBase#closeImpl()
	 */
	protected void closeImpl() {
		if (source!=null) {
			source.close();
		}
	}

	protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
		//System.out.println("writeImpl.len=" + len);
		if (source==null)
			createSource();

		byte[] b = toByteArray(samples, offs, len);
		source.write(b, 0, len*2);
	}

	/**
	 * Temporal = 0
	 * Residual = length+1024
	 * @param length
	 * @return
	 */
	protected byte[] getByteArray(int length) {
		if (byteBuf.length < length) {
			//System.out.println("new byteBuf.length " + (length+1024) );
			byteBuf = new byte[length+1024];
		}
		//byteBuf = new byte[4608+1024];
		return byteBuf;
	}

	protected byte[] toByteArray(short[] samples, int offs, int len) {
	//	System.out.println("toByteArray.len=" + len);
		byte[] b = getByteArray(len*2);
		int idx = 0;
		short s;
		while (len-- > 0) {
			s = samples[offs++];
			b[idx++] = (byte)s;
			b[idx++] = (byte)(s>>>8);
		}
		return b;
	}

	protected void flushImpl() {
		if (source!=null) {
			source.drain();
		}
	}

	public int getPosition() {
		int pos = 0;
		if (source!=null) {
			pos = (int)(source.getMicrosecondPosition()/1000);
		}
		return pos;
	}

	/**
	 * Temporal 1 + 22050/10
	 * Runs a short test by playing a short silent sound.
	 */
	public void test() throws JavaLayerException {
		try	{
			open(new AudioFormat(22050, 16, 1, true, false));
			short[] data = new short[22050/10];
			write(data, 0, data.length);
			flush();
			close();
		} catch (RuntimeException ex) {
			throw new JavaLayerException("Device test failed: "+ex);
		}

	}
}
