package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.Elevator;
import org.littletonrobotics.junction.Logger;

public class Superstructure extends SubsystemBase {
  private boolean requestIdle;
  private boolean requestFeed;
  private boolean requestPreScore;
  private boolean requestScore;
  private boolean requestDisable;

  private Elevator elevator;
  // private Arm arm;
  // private Funnel funnel;
  // private Claw claw;
  // private Climber climber;

  private Superstates state;
  private Level level;

  public static enum Superstates {
    IDLE,
    FEEDING,
    PRE_SCORE,
    // SCOREL2,
    // SCORE,
    DISABLED
  }

  public static enum Level {
    L2,
    L3,
    L4
  }

  public Superstructure(Elevator elevator) { // , Arm arm, Claw claw, Funnel funnel, Climber climber
    this.elevator = elevator;
    // this.arm = arm;
    // this.funnel = funnel;
    // this.claw = claw;
    // this.climber = climber;

    state = Superstates.IDLE;
    level = Level.L2;
  }

  @Override
  public void periodic() {
    Logger.recordOutput("Superstructure/State", state.toString());
    Logger.recordOutput("Superstructure/Level", level.toString());
    switch (state) {
      case IDLE:
        elevator.requestPosition(0);
        // arm.requestPosition(0);
        // funnel.requestIdle();
        // claw.requestIdle();
        // climber.requestIdle(0);

        if (requestFeed) {
          state = Superstates.FEEDING;
        } else if (requestPreScore) {
          state = Superstates.PRE_SCORE;
        }
        break;
      case FEEDING:
        elevator.requestPosition(0);
        // arm.requestPosition(0.03588729351758957);
        // claw.requestFeed();

        if (requestIdle) {
          state = Superstates.IDLE;
        }
        break;
      case PRE_SCORE:
        if (level == Level.L2) {
          // arm.requestPosition(0.20);
          elevator.requestPosition(-0.14);
        } else if (level == Level.L3) {
          // arm.requestPosition(0);
          elevator.requestPosition(-0.10);
        } else if (level == Level.L4) {
          // arm.requestPosition(0.70);
          elevator.requestPosition(-0.85);
        }
        // funnel.requestIdle();
        // climber.requestIdle(0);
        // claw.requestIdle();

        if (requestIdle) {
          state = Superstates.IDLE;
        } // else if (level == Level.L2
        //     && (requestScore /*&& elevator.atSetpoint()*/ /*&& claw.coralSecured()*/)) {
        //   state = Superstates.SCOREL2;
        // } else if (requestScore /*&& elevator.atSetpoint()*/ /*&& claw.coralSecured()*/) {
        //   state = Superstates.SCORE;
        // }
        break;
      case DISABLED:
        // arm.requestCoastMode(true);
        break;

        // case SCOREL2:
        //   claw.requestShootL2();

        //   // if (
        //   // /*!claw.coralSecured() &&*/ requestIdle) {
        //   //   state = Superstates.IDLE;
        //   // }
        //   break;
        // case SCORE:
        //   claw.requestShoot();

        //   // if (
        //   // /*!claw.coralSecured() &&*/ requestIdle) {
        //   //   state = Superstates.IDLE;
        //   // }
        //   break;
    }
  }

  public Superstates getState() {
    return state;
  }

  public void requestIdle() {
    unsetAllRequests();
    requestIdle = true;
  }

  public void requestFeed() {
    unsetAllRequests();
    requestFeed = true;
  }

  public void requestPreScore() {
    unsetAllRequests();
    requestPreScore = true;
  }

  public void requestScore() {
    unsetAllRequests();
    requestScore = true;
  }

  public void requestDisable() {
    unsetAllRequests();
    requestDisable = true;
  }

  private void unsetAllRequests() {
    requestIdle = false;
    requestFeed = false;
    requestPreScore = false;
    requestScore = false;
    requestDisable = false;
  }

  public void requestLevel(Level level) {
    this.level = level;
  }

  public void requestLevel(int level) {
    switch (level) {
      case 2:
        this.level = Level.L2;
        break;

      case 3:
        this.level = Level.L3;
        break;

      case 4:
        this.level = Level.L4;
        break;
    }
  }

  // public boolean pieceSecured() {
  //   return claw.coralSecured();
  // }
}
