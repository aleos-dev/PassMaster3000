package org.application.objects.website;

import lombok.Data;
import lombok.*;

import java.util.Objects;
@Getter
@ToString
public record Credentials(String login, String password) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credentials that)) return false;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
