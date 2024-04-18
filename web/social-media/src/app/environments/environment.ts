import getConfig from "../auth/shared/auth.configuration.dev";

export const environment = {
  production: false,
  authConfiguration: getConfig()
};
