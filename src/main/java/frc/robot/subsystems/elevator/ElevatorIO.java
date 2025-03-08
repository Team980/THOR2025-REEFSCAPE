package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
  @AutoLog
  public static class ElevatorIOInputs {
    public boolean kRoborioMotorConnected = false;
    public boolean kPDHMotorConnected = false;
    public double pos = 0.0;
    public double leaderVelMetersPerSecond = 0.0;
    public double leaderAppliedVoltage = 0.0;
    public double followerAppliedVoltage = 0.0;
    public double followerSupplyCurrentAmps = 0.0;
    public double leaderSupplyCurrentAmps = 0.0; // {leader, follower}
  }

  public default void updateInputs(ElevatorIOInputs inputs) {}

  public default void setHeight(double heightMeters) {}

  public default void stop() {}

  public default void enableBrakeMode(boolean enable) {}

  public default void enableCoastMode(boolean enable) {}
}
