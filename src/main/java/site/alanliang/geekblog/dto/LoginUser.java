package site.alanliang.geekblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.alanliang.geekblog.model.Menu;
import site.alanliang.geekblog.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/3/31 21:11
 * Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends User implements UserDetails {

    private List<Menu> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream()
                .map(p -> new SimpleGrantedAuthority(p.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(this.getStatus(), Status.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.getStatus(), Status.ENABLED);
    }
}

interface Status {
    Integer LOCKED = -1;
    Integer ENABLED = 1;
}