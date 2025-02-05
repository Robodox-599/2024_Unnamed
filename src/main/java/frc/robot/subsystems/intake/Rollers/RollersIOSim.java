package frc.robot.subsystems.intake.rollers;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class RollersIOSim implements RollersIO {
  private final DCMotorSim sim = new DCMotorSim(DCMotor.getKrakenX60(1), 1.2, 2);
  private PIDController controller =
      new PIDController(RollerConstants.kP, RollerConstants.kI, RollerConstants.kD);

  private double appliedVoltage = 0.0;
  private double desiredSpeed;

  public RollersIOSim() {
    // setPIDConstants(RollerConstants.kP, RollerConstants.kI, RollerConstants.kD);
  }

  @Override
  public void updateInputs(RollersIOInputs inputs) {
    sim.update(0.02);
    inputs.velocityRadsPerSec = sim.getAngularVelocityRadPerSec();
    inputs.appliedVoltage = appliedVoltage;
    inputs.currentAmps = sim.getCurrentDrawAmps();
    inputs.tempCelcius = 60;
    inputs.speedSetpoint = desiredSpeed;
  }

  @Override
  public void setVoltage(double volts) {
    appliedVoltage = volts;
    sim.setInputVoltage(volts);
  }

  @Override
  public void stop() {
    appliedVoltage = 0.0;
    sim.setInputVoltage(0.0);
  }

  @Override
  public void setSpeed(double speed) {
    desiredSpeed = speed;
    setVoltage(controller.calculate(sim.getAngularVelocityRadPerSec(), speed));
  }
}
