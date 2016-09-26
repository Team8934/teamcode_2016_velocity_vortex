/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This OpMode uses the common HDrive hardware class to define the devices on the robot.
 * All device access is managed through the HardwareHDrive class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes ProtoTim Teleop for an HDrive
 */

@TeleOp(name="ProtoTim?", group="HDrive")
public class ProtoTim extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareHDrive robot           = new HardwareHDrive();   // Use an HDrive's hardware
                                                               // could also use HardwarePushbotMatrix class.
    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        double side;
        double max;
        boolean aDebounce = false;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (gamepad1.a&&!aDebounce) {
                robot.mode=(byte)(robot.mode << 1);
                if(robot.mode==0)robot.mode=1;
            }
            aDebounce=gamepad1.a;
            if (robot.mode==0b1) {
                // Run wheels in arcade mode
                // In this mode the Left stick moves the robot up, down, left and right while the Right stick strafes left and right.
                left = gamepad1.left_stick_y - gamepad1.left_stick_x;
                right = gamepad1.left_stick_y + gamepad1.left_stick_x;
                side = gamepad1.right_stick_x;
            } else {
                // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
                // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
                // Use triggers to go left and right
                left  = -gamepad1.left_stick_y + gamepad1.right_stick_x;
                right = -gamepad1.left_stick_y - gamepad1.right_stick_x;
                side = gamepad1.left_stick_x;
            }

            // Normalize the values so neither exceed +/- 1.0
            max = Math.max(Math.abs(left), Math.abs(right));
            max = Math.max(max, Math.abs(side));
            if (max > 1.0)
            {
                left /= max;
                right /= max;
                side /= max;
            }

            robot.left.setPower(left);
            robot.right.setPower(right);
            robot.side.setPower(side);

            if (gamepad1.left_bumper) {
                robot.collect.setPower(1);
            } else {
                robot.collect.setPower(0);
            }
            if (gamepad1.right_bumper) {
                //robot.shoot.setPower(1);
            } else {
                //robot.shoot.setPower(0);
            }

            // Send telemetry message to signify robot running;
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.addData("Mode", robot.mode);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
