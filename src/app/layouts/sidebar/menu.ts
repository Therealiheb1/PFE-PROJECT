import { KeycloakService } from 'keycloak-angular';
import { MenuItem } from './menu.model';
import { AuthService } from 'src/app/pages/utility/app.guard';

const authService = new AuthService(new KeycloakService());

export const MENU: MenuItem[] = [
    {
        id: 1,
        label: 'MENUITEMS.MENU.TEXT',
        isTitle: true
    },
    {
        id: 2,
        icon: 'bx-home-circle',
        badge: {
            variant: 'info',
            text: 'MENUITEMS.DASHBOARDS.BADGE',
        },
        label: "Page d'acceuil",
        link: '/acceuil',
        parentId: 2
    },
    {
        id: 7,
        isLayout: true
    },
    {
        id: 8,
       
        isTitle: true
    },


    {
        id: 29,
        icon: 'bx bx-file-find',
       
       
              
                label: 'Liste des comptes',
                link: '/Admin',
                parentId: 29
         
    },
    {
        id: 7,
        isLayout: true
    },
    {
        id: 8,
       
        isTitle: true
    },


    {
        id: 29,
        icon: 'bx bx-transfer-alt',
       
       
              
                label: 'Transaction',
                link: '/tran',
                parentId: 29
         
    },
    {
        id: 7,
        isLayout: true
    },
    {
        id: 8,
       
        isTitle: true
    },

    {
        id: 29,
        icon: 'bx bxs-barcode',
       
       
              
                label: 'Les demande des chequier',
                link: '/cheque',
                parentId: 29
         
    },
    {
        id: 29,
        icon: 'bx-file',
       
       
              
                label: 'Les demande des chequier',
                link: '/box',
                parentId: 29
         
    },


 
    
];
export const getFilteredMenu = (): MenuItem[] => {
    const roles = authService.getUserRoles();
    return MENU.filter(menuItem => {
      if (menuItem.link === '/Admin' || menuItem.link === '/tran' || menuItem.link === '/cheque' || menuItem.link === '/box') {
        return roles.includes('ROLE_admin') || roles.includes('ROLE_superAdmin');
      }
      if (menuItem.link === '/dashboard' || menuItem.link === '/addacc') {
        return roles.includes('ROLE_superAdmin');
      }
      return true;
    });
  };