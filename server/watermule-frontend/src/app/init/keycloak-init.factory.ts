export class KeycloakInit {
}
import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(
  keycloak: KeycloakService
) {
  return () =>
    keycloak.init({
      config: {
        url: 'https://watermule.neusta-sd-west.de' + '/auth',
        realm: 'watermule',
        clientId: 'watermule-frontend',
      }
    });
}
