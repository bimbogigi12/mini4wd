package liquidShadow.mini4wd.rodaggio;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.wiringpi.SoftPwm;

public class Motor {

	private GpioPinDigitalOutput pinEnabled;
	private GpioPinDigitalOutput pinInput1;
	private GpioPinDigitalOutput pinInput2;
	
	private int minPower = 20;

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
		System.out.println("run at "+power+"% for "+duration+"seconds");
		
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
		
		int stepCount = delta/duration;
		int sleepTime = delta * 1000 / duration;
		int pin1Power = 0;
		int pin2Power = 0;
		
		while (stepCount > 0) {
			
			if (verse) {
				pin1Power = maxPower- delta;
			} else {
				pin2Power = maxPower- delta;
			}
			
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
		
		int delta = maxPower - minPower;
		
		int stepCount = delta/duration;
		int sleepTime = delta * 1000 / duration;
		int pin1Power = 0;
		int pin2Power = 0;
		
		while (stepCount > 0) {
			
			if (verse) {
				pin1Power = minPower+ delta;
			} else {
				pin2Power = minPower+ delta;
			}
			
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
