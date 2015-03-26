package com.taptap.game.model.monsters.factory;

public class UniversalMonsterTemplate {
    public UniversalMonsterTemplate(int health, int attackDamage, int attackSpeed) {
        this.health = health;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public void getDamage(int damage) {
        health-=damage;
    }

    public int attack() {
        return attackDamage;
    }

    private int health;
    private int attackDamage;
    private int attackSpeed;
}
