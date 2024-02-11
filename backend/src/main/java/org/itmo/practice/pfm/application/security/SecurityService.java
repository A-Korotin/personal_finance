package org.itmo.practice.pfm.application.security;

public interface SecurityService<ID> {
    boolean userHasAccessToView(ID id, String userId);
    boolean userHasAccessToModify(ID id, String userId);
}
