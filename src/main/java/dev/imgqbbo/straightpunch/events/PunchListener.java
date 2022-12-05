package dev.imgqbbo.straightpunch.events;

import dev.imgqbbo.straightpunch.StraightPunch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class PunchListener implements Listener {
    public PunchListener() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(StraightPunch.getInstance(), () -> arrows.entrySet().removeIf(set -> !set.getKey().isValid()), 10L, 0L);
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Entity proj = event.getProjectile();
        if (!(proj instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) proj;
        ProjectileSource shooter = arrow.getShooter();

        if (!(shooter instanceof Player)) {
            return;
        }

        Player player = (Player) shooter;

        double boost = arrow.getVelocity().length();
        int level = player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);

        Location location = player.getLocation();
        Vector direction = location.getDirection();

        boolean straight = StraightPunch.getFileManager().getConfig().getBoolean("punch.straight");

        if (StraightPunch.getFileManager().getConfig().contains("punch.levels." + level + ".straight")) {
            straight = StraightPunch.getFileManager().getConfig().getBoolean("punch.levels." + level + ".straight");
        }

        if (!straight) {
            return;
        }

        arrow.setVelocity(direction.multiply(boost));
        arrows.put(arrow, getFixedDirection(location, level));
    }

    @EventHandler
    public void onPlayerVelocity(PlayerVelocityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        EntityDamageEvent cause = player.getLastDamageCause();

        if (cause == null || cause.isCancelled() || !(cause instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent event1 = (EntityDamageByEntityEvent) cause;
        Entity damager = event1.getDamager();

        if (!(damager instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) damager;
        if (!arrows.containsKey(arrow)) {
            return;
        }

        ProjectileSource source = arrow.getShooter();
        if (source != player) {
            return;
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(StraightPunch.getInstance(), () ->
                player.setNoDamageTicks(0), 2L);
        event.setVelocity(arrows.get(arrow));
    }

    public Vector getFixedDirection(Location location, int level) {
        Vector vector = new Vector(0.0, 0.0, 0.0);

        double rotX = Math.toRadians(location.getYaw());

        vector.setX(-Math.sin(rotX));
        vector.setZ(Math.cos(rotX));

        double boost = StraightPunch.getFileManager().getConfig().getDouble("punch.horizontal");
        double vertical = StraightPunch.getFileManager().getConfig().getDouble("punch.vertical");

        if (StraightPunch.getFileManager().getConfig().contains("punch.levels." + level + ".horizontal")) {
            boost = StraightPunch.getFileManager().getConfig().getDouble("punch.levels." + level + ".horizontal");
            vertical = StraightPunch.getFileManager().getConfig().getDouble("punch.levels." + level + ".vertical");
        }

        double factor = Math.max(1, level) * boost;
        return vector.multiply(factor).setY(vertical);
    }

    Map<Arrow, Vector> arrows = new HashMap<>();
}