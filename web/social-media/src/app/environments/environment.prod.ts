import getConfig from "../auth/shared/configuration/auth.configuration.prod";

export const environment = {
  production: true,
  authConfiguration: getConfig()
};
