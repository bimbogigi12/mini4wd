package liquidShadow.mini4wd.rodaggio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.wiringpi.SoftPwm;



public class Motor {

	private GpioPinDigitalOutput pinEnabled;
	private GpioPinDigitalOutput pinInput1;
	private GpioPinDigitalOutput pinInput2;
	
	private int minPower = 20;
	
	private static Logger LOG = LogManager.getLogger(Motor.class);

	public Motor(GpioPinDigitalOutput pinEnabled, GpioPinDigitalOutput pinInput1, GpioPinDigitalOutput pinInput2) {
		this.pinEnabled = pinEnabled;
		this.pinInput1 = pinInput1;
		this.pinInput2 = pinInput2;
		
		SoftPwm.softPwmCreate(pinInput1.getPin().getAddress(), minPower, 100);
		SoftPwm.softPwmCreate(pinInput2.getPin().getAddress(), minPower, 100);
	}

	public void run(int power, int duration, boolean verse) {
		pinEnabled.high();
		int pin1Power = 0;
		int pin2Power = 0;
		
		if (verse) {
			pin1Power = power;
		} else {
			pin2Power = power;
		}
		LOG.debug("run at "+power+"% for "+duration+"seconds");
		
		SoftPwm.softPwmWrite(pinInput1.getPin().getAddress(), pin1Power);
		SoftPwm.softPwmWrite(pinInput2.getPin().getAddress(), pin2Power);
		
		try {
			Thread.sleep(duration*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SoftPwm.softPwmWrite(pinInput1.getPin().getAddress(), 0);
		SoftPwm.softPwmWrite(pinInput2.getPin().getAddress(), 0);
		
		pinEnabled.low();
	}
	
	public void increment(int maxPower, int duration, boolean verse) {
		pinEnabled.high();
		
		int delta = maxPower - minPower;
		
		int stepCount = delta*1000/duration;
		int sleepTime = delta * 1000 / duration;
		int pin1Power = 0;
		int pin2Power = 0;
		
		
		LOG.debug(stepCount+" steps "+sleepTime+" waiting");
		
		while (delta >= 0) {
			
			if (verse) {
				pin1Power = maxPower- delta;
			} else {
				pin2Power = maxPower- delta;
			}
			
			LOG.debug("incremnting pin1 to "+pin1Power+"% for "+sleepTime+" millis");
			LOG.debug("incremnting pin2 to "+pin2Power+"% for "+sleepTime+" millis");
			
			SoftPwm.softPwmWrite(pinInput1.getPin().getAddress(), pin1Power);
			SoftPwm.softPwmWrite(pinInput2.getPin().getAddress(), pin2Power);
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stepCount--;
			delta--;
		}
		
		SoftPwm.softPwmWrite(pinInput1.getPin().getAddress(), 0);
		SoftPwm.softPwmWrite(pinInput2.getPin().getAddress(), 0);
		
		pinEnabled.low();
	}
	
	public void decrement(int maxPower, int duration, boolean verse) {
		pinEnabled.high();
		
		int delta = minPower;
		
		int stepCount = delta*1000/duration;
		int sleepTime = delta * 1000 / duration;
		int pin1Power = 0;
		int pin2Power = 0;
		
		LOG.debug(stepCount+" steps "+sleepTime+" waiting");
		
		
		while (delta <= 100) {
			
			if (verse) {
				pin1Power = minPower+ delta;
			} else {
				pin2Power = minPower+ delta;
			}
			
			LOG.debug("decremnting pin1 to "+pin1Power+"% for "+sleepTime+" millis");
			LOG.debug("decremnting pin2 to "+pin2Power+"% for "+sleepTime+" millis");
			
			SoftPwm.softPwmWrite(pinInput1.getPin().getAddress(), pin1Power);
			SoftPwm.softPwmWrite(pinInput2.getPin().getAddress(), pin2Power);
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stepCount--;
			delta++;
		}
		
		SoftPwm.softPwmWrite(pinInput1.getPin().getAddress(), 0);
		SoftPwm.softPwmWrite(pinInput2.getPin().getAddress(), 0);
		
		pinEnabled.low();
	}

	
}
