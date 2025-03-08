// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.constants;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static final String kCANivore = "CANmeloAnthony";

  public static final boolean elevatorEnabled = true;

  public class Elevator {

    // NEOS
    public static final int kElevatorPDH = 3; // Clockwise
    public static final int kElevatorRoboRio = 4; // Counterclockwise

    public static final int EncoderDIO2 = 2;
    public static final int EncoderDIO3 = 3;

    public static final double setpointToleranceMeters = 0.01;

    public static final int supplyCurrentLimit = 40;

    public static final double minOutput = -1;
    public static final double maxOutput = 1;

    // public static final double innerStageWeight = 12.0; // lbs

    // public static final double gearRatio = 9.0;
    // public static final double sprocketDiameter = Units.inchesToMeters(1.751); // pitch diameter
    // public static final double mechanismMaxAccel = 3.3274;
    // public static final double mechanismMaxCruiseVel = 1.597152;

  }

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }
}
