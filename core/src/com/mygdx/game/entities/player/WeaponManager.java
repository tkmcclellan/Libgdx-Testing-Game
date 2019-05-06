package com.mygdx.game.entities.player;

import com.mygdx.game.entities.weapons.WeaponActor;

public class WeaponManager {
    private WeaponActor[] weapons;
    private int numWeapons, selected = 0;

    public WeaponManager(int numWeapons) {
        this.numWeapons = numWeapons;

        weapons = new WeaponActor[numWeapons];

        for (int i = 0; i < numWeapons; i++) {
            weapons[i] = null;
        }
    }

    public void update(int direction, int scrolled) {
        if (weapons[selected] != null) {
            weapons[selected].update(direction);
        }
        selected = (selected + scrolled + numWeapons) % numWeapons;
    }

    public void addWeapon(WeaponActor weapon, int index) {
        if (index == -1) {
            for (int i = 0; i < weapons.length; i++) {
                if (weapons[i] == null) {
                    weapons[i] = weapon;
                    break;
                }
            }
        } else if (index >= 0 && index < weapons.length) {
            weapons[index] = weapon;
        }
    }

    public void removeWeapon(int index) {
        if (index >= 0 && index < weapons.length) {
            if (weapons[selected] != null) {
                weapons[selected].remove();
            }
            weapons[selected] = null;
        }
    }

    public void setWeaponVisibility(boolean b) {
        if (weapons[selected] != null) {
            weapons[selected].setVisible(b);
        }
    }
}
