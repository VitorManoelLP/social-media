import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { Keycloak } from './app/auth/shared/keycloak.wrapper.service';
import Profile from './app/auth/shared/profile';

const keycloak = new Keycloak();

async function initialize(): Promise<void> {
  try {
    await keycloak.initKeycloak();
    const profile = await keycloak.loadUserProfile();
    Profile.getInstance().attrUser = profile;
  } catch (error) {
    console.error(error);
  } finally {
    await bootstrapApplication(AppComponent, appConfig);
  }
}

initialize();

export default keycloak;

