import getConfig from "../auth/shared/auth.configuration.prod";

export const environment = {
  production: true,
  authConfiguration: getConfig()
};
