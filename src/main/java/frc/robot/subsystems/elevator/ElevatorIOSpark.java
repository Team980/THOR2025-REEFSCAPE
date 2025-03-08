package frc.robot.subsystems.elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.constants.Constants;

public class ElevatorIOSpark implements ElevatorIO {
  private SparkBase leader;
  private SparkBase follower;

  private RelativeEncoder encoder;

  private SparkMaxConfig leaderConfig;
  private SparkMaxConfig followerConfig;

  public ElevatorIOSpark() {
    leader =
        new SparkMax(
            Constants.Elevator.kElevatorRoboRio,
            MotorType.kBrushless); // The leader is on the side of the robo rio
    follower =
        new SparkMax(
            Constants.Elevator.kElevatorPDH,
            MotorType.kBrushless); // The follower is on the side of the PDH

    leaderConfig = new SparkMaxConfig();
    followerConfig = new SparkMaxConfig();

    configureLeader(leader, leaderConfig);
    configureFollower(follower, followerConfig);
  }

  private void configureLeader(SparkBase motor, SparkBaseConfig config) {

    encoder = motor.getEncoder();
    encoder.setPosition(0);
    // encoder.setPositionConversionFactor(Constants.Elevator.encoderConversionFactor);

    config.idleMode(IdleMode.kBrake);
    config.inverted(false);
    config.limitSwitch.forwardLimitSwitchEnabled(false);
    config.limitSwitch.forwardLimitSwitchEnabled(false);
    config.smartCurrentLimit(Constants.Elevator.supplyCurrentLimit);
    config.closedLoop.pid(3, 0, 0.0);
    config.closedLoop.outputRange(Constants.Elevator.minOutput, Constants.Elevator.maxOutput);

    motor.configure(config, null, null);
  }

  private void configureFollower(SparkBase motor, SparkBaseConfig config) {

    config.follow(leader, true);
    config.idleMode(IdleMode.kBrake);
    config.limitSwitch.forwardLimitSwitchEnabled(false);
    config.limitSwitch.forwardLimitSwitchEnabled(false);
    config.smartCurrentLimit(Constants.Elevator.supplyCurrentLimit);

    motor.configure(config, null, null);
  }

  @Override
  public void updateInputs(ElevatorIOInputs inputs) {
    inputs.kRoborioMotorConnected = (leader.getFirmwareVersion() != 0);
    inputs.kPDHMotorConnected = (leader.getFirmwareVersion() != 0);
    inputs.pos = leader.getEncoder().getPosition();
    inputs.leaderVelMetersPerSecond = leader.getEncoder().getVelocity();
    inputs.leaderAppliedVoltage = leader.getBusVoltage();
    inputs.leaderSupplyCurrentAmps = leader.getOutputCurrent();
    inputs.followerAppliedVoltage = follower.getBusVoltage();
    inputs.followerSupplyCurrentAmps = follower.getOutputCurrent();
  }

  @Override
  public void setHeight(double targetPosition) {
    leader.getClosedLoopController().setReference(targetPosition, ControlType.kPosition);
  }

  @Override
  public void stop() {
    leader.stopMotor();
    follower.stopMotor();
  }

  @Override
  public void enableBrakeMode(boolean enable) {
    leaderConfig.idleMode(IdleMode.kBrake);
    followerConfig.idleMode(IdleMode.kBrake);
  }

  @Override
  public void enableCoastMode(boolean enable) {
    leaderConfig.idleMode(IdleMode.kCoast);
    followerConfig.idleMode(IdleMode.kCoast);
  }
}
