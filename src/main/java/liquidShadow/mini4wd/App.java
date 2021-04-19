package liquidShadow.mini4wd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import liquidShadow.mini4wd.rodaggio.Contagiri;
import liquidShadow.mini4wd.rodaggio.Motor;

public class App {

	public static GpioPinDigitalOutput pin1 = null;
	public static GpioPinDigitalOutput pin2 = null;
	public static GpioPinDigitalOutput pin3 = null;
	public static GpioPinDigitalInput pin4 = null;
	
	private static Logger LOG = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		for (String arg : args) { 

			LOG.info("arg "+arg);
			
			
			if ("Rodaggio".equals(arg)) {
				runRodaggio();
			}
			
			if ("Contagiri".equals(arg)) {
				contagiri();
			}
		}
	}
	
	private static void contagiri() {
		Contagiri contagiri = new Contagiri(getPin4());
		
		contagiri.read(60*1000);
	}

	private static void runRodaggio() {
		LOG.info("Starting rodaggio");
		try {
			int sleepTime = 60 * 1000;
			Motor motor = new Motor(getPin1(), getPin2(), getPin3());

			motor.run(50, 120, true);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.run(50, 120, false);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.increment(100, 120, true);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.increment(100, 120, false);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.decrement(100, 120, true);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.decrement(100, 120, false);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.run(100, 120, true);
			LOG.debug("sleep for "+sleepTime+" millis");
			Thread.sleep(sleepTime);
			motor.run(100, 120, false);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LOG.info("Ended rodaggio");
	}

	private static GpioPinDigitalOutput getPin1() {
		if (pin1 == null) {
			final GpioController gpio = GpioFactory.getInstance();
			pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "PinLED", PinState.LOW);
		}
		return pin1;
	}

	private static GpioPinDigitalOutput getPin2() {
		if (pin2 == null) {
			final GpioController gpio = GpioFactory.getInstance();
			pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "PinLED", PinState.LOW);
		}
		return pin2;
	}

	private static GpioPinDigitalOutput getPin3() {
		if (pin3 == null) {
			final GpioController gpio = GpioFactory.getInstance();
			pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "PinLED", PinState.LOW);
		}
		return pin3;
	}

	private static GpioPinDigitalInput getPin4() {
		if (pin4 == null) {
			final GpioController gpio = GpioFactory.getInstance();
			pin4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
			pin4.setShutdownOptions(true);
		}
		return pin4;
	}
	
}
