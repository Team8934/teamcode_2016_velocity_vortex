package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case it's for (name here).
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  1a:        "m1"
 * Motor channel:  1b:        "m2"
 * Motor channel:  2a:        "name"
 * Motor channel:  2b:        "name"
 * Motor channel:  3a:        "name"
 * Motor channel:  3b:        "name"
 * Motor channel:  4a:        "name"
 * Motor channel:  4b:        "name"
 * Servo channel:  1:         "big"
 * Servo channel:  2:         "small"
 * Servo channel:  3:         "name"
 * Servo channel:  4:         "name"
 * Servo channel:  5:         "name"
 * Servo channel:  6:         "name"
 * Servo channel:  7:         "name"
 * Servo channel:  8:         "name"
 * Servo channel:  9:         "name"
 * Servo channel:  10:        "name"
 * Servo channel:  11:        "name"
 * Servo channel:  12:        "name"
 *
 */
public class HardwareTestbot
{
    /* Public OpMode members. */
    public DcMotor m1 = null;
    public DcMotor m2 = null;
    public Servo big = null;
    public Servo small = null;

    /* Public vars here */

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareTestbot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        m1 = hwMap.dcMotor.get("m1");
        m2 = hwMap.dcMotor.get("m2");

        // Set all motors to zero power
        m1.setPower(0);
        m2.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m1.setMaxSpeed(1478);
        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m2.setMaxSpeed(1478);

        // Define and initialize ALL installed servos.
        big = hwMap.servo.get("l");
        small = hwMap.servo.get("s");
        big.setPosition(0);
        small.setPosition(0);
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
