package frc.robot.subsystems.intake.wrist;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeWristIO {

  @AutoLog
  public static class IntakeWristIOInputs {
    public double angleRads = 0.0;
    public double angVelocityRadsPerSec = 0.0;
    public double appliedVolts = 0.0;
    public double setpointAngleRads = 0.0;
    public double currentAmps = 0.0;
    public double tempCelsius = 0.0;
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(IntakeWristIOInputs inputs) {}

  /** Run open loop at the specified voltage. */
  public default void setVoltage(double motorVolts) {}
  /** Returns the current distance measurement. */
  public default double getAngle() {
    return 0.0;
  }

  /** Sets the pivot arm voltage to 0 */
  public default void stop() {}

  /** Go to Setpoint */
  public default void goToSetpoint(double setpoint) {}

  public default void holdSetpoint(double setpoint) {}

  public default void setBrake(boolean brake) {}

  public default boolean atSetpoint() {
    return false;
  }

  public default void setP(double p) {}

  public default void setI(double i) {}

  public default void setD(double d) {}

  public default void setFF(double ff) {}

  public default void setkS(double kS) {}

  public default void setkV(double kV) {}

  public default void setkG(double kG) {}

  public default void setkA(double kA) {}

  public default double getP() {
    return 0.0;
  }

  public default double getI() {
    return 0.0;
  }

  public default double getD() {
    return 0.0;
  }

  public default double getFF() {
    return 0.0;
  }

  public default double getkS() {
    return 0.0;
  }

  public default double getkG() {
    return 0.0;
  }

  public default double getkV() {
    return 0.0;
  }

  public default double getkA() {
    return 0.0;
  }
}
