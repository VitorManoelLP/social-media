export default interface UserFound {
  id: string;
  name: string;
  nickname: string;
  alreadyRequested: boolean;
  hasRequest: boolean;
  [key: string]: any;
}
