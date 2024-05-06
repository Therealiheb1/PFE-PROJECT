import { MenuItem } from './menu.model';

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
                link: '/Admin',
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
        icon: 'bx-file',
       
       
              
                label: 'registrer un nouveau client',
                link: '/adduser',
                parentId: 29
         
    },

 
    
];

