package liquidShadow.mini4wd.rodaggio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioPinDigitalInput;

public class Contagiri {
	private GpioPinDigitalInput pinInput1;

	private static Logger LOG = LogManager.getLogger(Contagiri.class);

	public Contagiri(GpioPinDigitalInput pinInput1) {
		this.pinInput1 = pinInput1;
	}

	public void read(int duration) {

		int count = 0;

		int sleepTime = 10;
		int timeLeft = duration;
		try {
			while (timeLeft < 0) {
				boolean isHigh = pinInput1.getState().isHigh();
				if (isHigh) {
					count++;
					LOG.debug("pin on");
				} else {
					LOG.debug("pin off");
				}
				Thread.sleep(sleepTime);
				timeLeft -= sleepTime;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		LOG.debug(count + " giri in " + duration + " millis");
	}

}
