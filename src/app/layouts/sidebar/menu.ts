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
                link: '/dashboard',
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
        
        icon: 'bx bx-bell',
       
              
                label: 'notification',
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
        
        icon: 'bx bxs-barcode',
       
                
                label: 'demande de chequier ',
                link: '/cheque',
                parentId: 72
        
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
        
        icon: 'bx bx-headphone',
       
                
                label: 'FAQS / Support',
                link: '/support',
                parentId: 72
        
    },
    
];

