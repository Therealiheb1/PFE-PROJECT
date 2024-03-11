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
                link: '/comptes',
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
        
        icon: 'bx-envelope',
       
              
                label: 'gérer compte',
                link: '/dashboard',
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
        
        icon: 'bx-envelope',
       
              
                label: 'MENUITEMS.EMAIL.LIST.INBOX',
                link: '/email/inbox',
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
        id: 11,
        label: 'Mes informations',
        icon: 'bx-file',
        link: '/filemanager',
        badge: {
            variant: 'success',
            text: 'MENUITEMS.FILEMANAGER.BADGE',
        },
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
        id: 72,
        
        icon: 'bx-file',
       
                
                label: 'FAQS / Support',
                link: '/pages/faqs',
                parentId: 72
        
    },
    
];

