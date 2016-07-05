package org.usfirst.frc.team2383.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically it 
 * contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
//2384
public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick leftStick;  // set to ID 1 in DriverStation
    Joystick rightStick; // set to ID 2 in DriverStation
    Joystick operatorStick;
    CANTalon lift;
    DoubleSolenoid pusher;
    
    public Robot() {
        myRobot = new RobotDrive(9, 8,1,0);
        
        myRobot.setExpiration(0.1);
        leftStick = new Joystick(1);
        rightStick = new Joystick(0);
        operatorStick = new Joystick(2);
        lift = new CANTalon(0);
        pusher = new DoubleSolenoid(4,5);
        
        
        lift.enableBrakeMode(true);
        //lift.setForwardSoftLimit(forwardLimit);
        //lift.enableForwardSoftLimit(enable);
        
        //lift.setReverseSoftLimit(reverseLimit);
        //lift.enableReverseSoftLimit(enable);
    }

    
    /**
     * Runs the motors with tank steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	myRobot.tankDrive(leftStick, rightStick);
        	myRobot.setMaxOutput(0.4);
            Timer.delay(0.005);		// wait for a motor update time
            
            
            // lift
            
            boolean liftUp = operatorStick.getRawButton(5);
            boolean liftDown = operatorStick.getRawButton(4);
            
            if((liftUp == true) && (liftDown == false)){
            	lift.set(0.3);
            }
            else if((liftUp == false) && (liftDown == false)){
            	lift.set(0.0);
            }
            else if((liftUp == false) && (liftDown == true)){
            	lift.set(-0.3);
            }
            
            
            // pusher
            
            boolean pusherForward = operatorStick.getRawButton(3);
            boolean pusherBackward = operatorStick.getRawButton(2);
            
            
            if((pusherForward) && (pusherBackward == false)){
            	pusher.set(Value.kForward);
            }
            
            else if((pusherForward == false) && (pusherBackward == false)){
            	pusher.set(Value.kOff);
            }
            
            else if((pusherForward == false) && (pusherBackward)){
            	pusher.set(Value.kReverse);
            }
            
        }
    }

}
