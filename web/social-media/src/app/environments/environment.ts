import getConfig from "../auth/shared/configuration/auth.configuration.dev";

export const environment = {
  production: false,
  authConfiguration: getConfig()
};
