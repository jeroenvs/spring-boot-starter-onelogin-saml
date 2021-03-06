package nl._42.boot.onelogin.saml;

import com.onelogin.saml2.Auth;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class Saml2Response {

    private final Auth auth;

    public String getName() {
        return auth.getNameId();
    }

    public Collection<String> getAttributes() {
        List<String> names = auth.getAttributesName();
        return Collections.unmodifiableList(names);
    }

    public Set<String> getValues(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptySet();
        }

        Collection<String> attribute = auth.getAttribute(name);
        if (attribute == null) {
            return Collections.emptySet();
        }

        return new HashSet<>(attribute);
    }

    public Optional<String> getValue(String attribute) {
        return getValues(attribute)
            .stream()
            .sorted()
            .filter(StringUtils::isNotBlank)
            .findFirst();
    }

}
