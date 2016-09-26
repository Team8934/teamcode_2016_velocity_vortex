package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is an HDrive.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left"
 * Motor channel:  Right drive motor:        "right"
 * Motor channel:  Side drive motor:         "side"
 * Motor channel:  Collector motor:          "collect"
 * Motor channel:  Shooter motor:            "shoot"
 */
public class HardwareHDrive
{
    /* Public OpMode members. */
    public DcMotor left = null;
    public DcMotor right = null;
    public DcMotor side = null;
    public DcMotor collect = null;
    //public DcMotor shoot = null;
    public byte mode = 0b1;

    /* local OpMode members. */
    HardwareMap hwMap           = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareHDrive(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        left = hwMap.dcMotor.get("left");
        right = hwMap.dcMotor.get("right");
        side = hwMap.dcMotor.get("side");
        collect = hwMap.dcMotor.get("collect");
        //shoot = hwMap.dcMotor.get("shoot");
        left.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        right.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        collect.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        left.setPower(0);
        right.setPower(0);
        side.setPower(0);
        collect.setPower(0);
        //shoot.setPower(0);
        mode = 0;

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        side.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        collect.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //shoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

