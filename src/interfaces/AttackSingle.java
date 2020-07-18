package interfaces;

import entity.CombatableEntity;

/**
 * 攻击个体实体接口
 * @author Administrator
 */
public interface AttackSingle {
  public int attackSingle(CombatableEntity A,CombatableEntity B);
}
