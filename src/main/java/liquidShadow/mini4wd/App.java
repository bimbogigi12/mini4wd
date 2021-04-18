package liquidShadow.mini4wd;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import liquidShadow.mini4wd.rodaggio.Motor;

public class App {

	public static GpioPinDigitalOutput pin1 = null;
	public static GpioPinDigitalOutput pin2 = null;
	public static GpioPinDigitalOutput pin3 = null;
	public static GpioPinDigitalOutput pin4 = null;

	public static void main(String[] args) {

		runRodaggio();
	}

	private static void runRodaggio() {
		System.out.println("Starting rodaggio");
		try {
			int sleepTime = 60 * 1000;
			Motor motor = new Motor(getPin1(), getPin2(), getPin3());

//			motor.run(50, 120, true);
//			Thread.sleep(sleepTime);
//			motor.run(50, 120, false);
//			Thread.sleep(sleepTime);
			motor.increment(100, 120, true);
			Thread.sleep(sleepTime);
			motor.increment(100, 120, false);
			Thread.sleep(sleepTime);
			motor.decrement(100, 120, true);
			Thread.sleep(sleepTime);
			motor.decrement(100, 120, false);
			Thread.sleep(sleepTime);
			motor.run(100, 120, true);
			Thread.sleep(sleepTime);
			motor.run(100, 120, false);
			Thread.sleep(sleepTime);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Ended rodaggio");
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

	private static GpioPinDigitalOutput getPin4() {
		if (pin4 == null) {
			final GpioController gpio = GpioFactory.getInstance();
			pin4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "PinLED", PinState.LOW);
		}
		return pin4;
	}
}
