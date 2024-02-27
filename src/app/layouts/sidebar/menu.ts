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
        id: 57,
        label: 'MENUITEMS.AUTHENTICATION.TEXT',
        icon: 'bx-user-circle',
        badge: {
            variant: 'success',
            text: 'MENUITEMS.AUTHENTICATION.BADGE',
        },
        subItems: [
            {
                id: 58,
                label: 'MENUITEMS.AUTHENTICATION.LIST.LOGIN',
                link: '/account/login',
                parentId: 57
            },
           
            {
                id: 60,
                label: 'MENUITEMS.AUTHENTICATION.LIST.REGISTER',
                link: '/account/signup',
                parentId: 57
            },
            
            {
                id: 62,
                label: 'MENUITEMS.AUTHENTICATION.LIST.RECOVERPWD',
                link: '/account/reset-password',
                parentId: 57
            },
           
            {
                id: 66,
                label: 'MENUITEMS.AUTHENTICATION.LIST.CONFIRMMAIL',
                link: '/pages/confirm-mail',
                parentId: 57
            },
            
            {
                id: 68,
                label: 'MENUITEMS.AUTHENTICATION.LIST.EMAILVERIFICATION',
                link: '/pages/email-verification',
                parentId: 57
            },
           
            {
                id: 70,
                label: 'MENUITEMS.AUTHENTICATION.LIST.TWOSTEPVERIFICATION',
                link: '/pages/two-step-verification',
                parentId: 57
            },
          
        ]
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

