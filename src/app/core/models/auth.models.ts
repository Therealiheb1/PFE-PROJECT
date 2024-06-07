export class User {
    id: number;
    username: string;
    password: string;
    firstName?: string;
    lastName?: string;
    token?: string;
    email: string;
}
export class Customers {
    id: number;
    firstName?: string;
    lastName?: string;
    email: string;
    cin: number;
    tel: number;
    daten : number;
    sexe: string;
    agence: string;
    profession: string;
}
export class compte {
   
    rib?: string;
    iban?: string;
    NumeroCompte: string;
    client: number;
    solde: number;
    statues : boolean;
    sexe: string;
    agence: string;
    profession: string;
}