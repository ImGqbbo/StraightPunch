package me.gabbo.flexpunch;

import me.gabbo.flexpunch.utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PunchModule implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerVelocity(PlayerVelocityEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();
        EntityDamageEvent cause = p.getLastDamageCause();

        if (cause == null || cause.isCancelled() || !(cause instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) cause;

        if (!(event.getDamager() instanceof Arrow)) return;
        Arrow a = (Arrow) event.getDamager();

        if (a.getShooter() != p) return;
        Vector vector = getVector(e.getPlayer(), a, a.getLocation().getPitch() < -85);

        e.setVelocity(vector.setX(vector.getX() * ((a.getLocation().getPitch() < -85) ? 1.0f : -1.0f)));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArrowShoot(EntityShootBowEvent e) {
        if (!(e.getProjectile() instanceof Arrow)) return;
        Arrow a = (Arrow) e.getProjectile();

        if (!(a.getShooter() instanceof Player)) return;
        Player shooter = (Player) a.getShooter();

        a.setVelocity(getArrowStraight(a, shooter));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArrowHit(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;

        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Arrow a = (Arrow) e.getDamager();

        if (!(a.getShooter() instanceof Player)) return;
        Player target = (Player) e.getEntity();
        Player shooter = (Player) a.getShooter();

        if (target != shooter) return;
        Bukkit.getScheduler().runTaskLaterAsynchronously(FlexPunch.plugin, () -> target.setNoDamageTicks(0), 2L);

        if (target.getNoDamageTicks() > target.getMaximumNoDamageTicks() / 2) return;
        target.setVelocity(getVector(target, a, a.getLocation().getPitch() < -85));
    }

    private Vector getArrowStraight(Arrow arrow, Player player) {
        return player.getLocation().getDirection().multiply(arrow.getVelocity().length());
    }

    private double getValue(String regex, int lvl) {
        if (FileUtil.ConfigurationFile.getBoolean("punch.global")) {
            return FileUtil.ConfigurationFile.getDouble("punch." + regex);
        } else if (FileUtil.ConfigurationFile.contains("punch.levels." + lvl)) {
            return FileUtil.ConfigurationFile.getDouble("punch.levels." + lvl + "." + regex);
        }

        return regex == "horizontal" ? 0.9 : 0.42;
    }

    private Vector getVector(Player target, Arrow arrow, boolean switcher) {
        ItemStack bow = target.getItemInHand();
        int lvl = bow.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);

        return (switcher ? target : arrow).getLocation().getDirection().setY(0.0).multiply(Math.max(1,lvl) * getValue("horizontal", lvl))
                .setY(getValue("vertical", lvl));
    }
}