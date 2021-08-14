package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static int medikHealth = 300;
    public static int medikTreat = 0;
    public static int golemHealth = 500;
    public static int golemDamage = 10;
    public static int luckyHealth = 350;
    public static int luckyDamage = 25;
    public static int berserkHealth = 250;
    public static int berserkDamage = 25;
    public static int thorHealth = 320;
    public static int thorDamage = 30;

    public static String bossDefence = "";
    public static int[] heroesHealth = {260, 250, 270, medikHealth, 500, luckyHealth, berserkHealth, thorHealth};
    public static int[] heroesDamage = {15, 20, 10, medikTreat, golemDamage, luckyDamage, berserkDamage, thorDamage};
    public static String[] heroesAttackType = {
            "Physical", "Magical", "Kinetic", "Medik", "Golem", "lucky", "Berserk", "Thor"};
    static Random random = new Random();


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        if (bossHealth > 0) {
            if (!thorStun()) {
                chooseBossDefence();
                bossHits();
                medikHit();
                golemAttack();
                luckyDodge();
                berserkBlock();
            } else {
                System.out.println("BOSS STUN");
            }
            heroesHit();
            printStatistics();
        }

    }

    public static boolean thorStun() {
        return random.nextBoolean();
    }

    public static void berserkBlock() {
        int block = random.nextInt(bossDamage);
        if (berserkHealth > 0) {
            heroesHealth[6] += block;
            bossHealth -= block;
            System.out.println("Berserk blocked: " + block);
        }
    }

    public static void golemAttack() {
        int partDamage = bossDamage / 5;
        int aliveHeroes = 0;

        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i == 4) continue;
                else if (heroesHealth[i] > 0) {
                    heroesHealth[i] += partDamage;
                    aliveHeroes++;
                }
            }
            heroesHealth[4] -= (aliveHeroes * partDamage);
            System.out.println("Golem take: " + aliveHeroes * partDamage);
        }
    }

    public static void luckyDodge() {
        boolean dodge = random.nextBoolean();

        if (dodge && heroesHealth[5] > 0) {
            heroesHealth[5] += bossDamage - (bossDamage / 5);
            System.out.println("LUCKY DODGED!");
        }

    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length);
        if (randomIndex == 3) chooseBossDefence();
        else {
            bossDefence = heroesAttackType[randomIndex];
            System.out.println("Boss choose: " + bossDefence);
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }


        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void medikHit() {
        int randomHero = random.nextInt(heroesAttackType.length);

        if (heroesHealth[3] > 0 && heroesHealth[randomHero] < 100 && heroesHealth[randomHero] > 0) {
            heroesHealth[randomHero] = heroesHealth[randomHero] + 30;
            System.out.println("Medic heal: " + heroesAttackType[randomHero]);
        }

    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefence == heroesAttackType[i]) {
                    int coeff = random.nextInt(9) + 2;
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println(
                            "Critical Damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("++++++++++++++");
        System.out.println("Boss health: " + bossHealth + " ["
                + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i] + " ["
                    + heroesDamage[i] + "]");
        }
        System.out.println("++++++++++++++");
    }
}

/*ДЗ:
ДЗ на сообразительность:
● Добавить n-го игрока, Golem, который имеет увеличенную жизнь но слабый удар.
Может принимать на себя 1/5 часть урона исходящего от босса по другим игрокам.
● Добавить n-го игрока, Lucky, имеет шанс уклонения от ударов босса.
● Добавить n-го игрока, Berserk, блокирует часть удара босса по себе и прибавляет
заблокированный урон к своему урону и возвращает его боссу
● Добавить n-го игрока, Thor, удар по боссу имеет шанс оглушить босса на 1 раунд,
вследствие чего босс пропускает 1 раунд и не наносит урон героям. //
random.nextBoolean(); - true, false
*/
