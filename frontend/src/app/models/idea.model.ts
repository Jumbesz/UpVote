export interface IdeaModel {
  id: number,
  name: string,
  description: string,
  rating: number,
  isApproved: boolean
  hasVoted?: boolean;
}
