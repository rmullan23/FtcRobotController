package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
//@Disabled
public class ArkansasScrimmage extends LinearOpMode{
    private DcMotor frontRight = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor collector = null;
    private DcMotor arm = null;
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        arm = hardwareMap.get(DcMotor.class, "arm");

        collector = hardwareMap.get(DcMotor.class, "collector");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm.setMode(DcMotorController.RunMode.Run_To_Position);

        waitForStart();
        runtime.reset();


        while (opModeIsActive()) {

            double vertical, horizontial, rotation;

            //double drive = -gamepad1.left_stick_y;
            //double crab  =  gamepad1.left_stick_x;
            //double turn  =  gamepad1.right_stick_x;
            double cl    =  gamepad1.right_trigger;

           /* leftPower    = Range.clip(drive + crab, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - crab, -1.0, 1.0) ;
            frontLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
*/
            vertical = gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
             horizontial = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
            rotation = -gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);
            frontLeft.setPower(Range.clip(vertical - horizontial + rotation,-1,1));
            frontRight.setPower(Range.clip(vertical + horizontial - rotation,-1,1));
            backLeft.setPower(Range.clip(vertical + horizontial + rotation,-1,1));
            backRight.setPower(Range.clip(vertical - horizontial - rotation,-1,1));

            telemetry.update();
            if(gamepad1.dpad_right){
                arm.setTargetPosition(0);
            }
            else if(gamepad1.dpad_left){
                arm.setTargetPosition(10);
            }
//collecotor
            if(gamepad1.right_bumper){
                cl=1;
            }
            else if(gamepad1.left_bumper){
                cl=-1;
            }
            else{
                cl=0;
            }
            collector. setPower(Range.clip(cl,-1,1));

        }
    }
}