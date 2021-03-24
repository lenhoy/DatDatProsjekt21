# DatDatProsjekt21

## Post Controller

En post controller lages ved å sende inn ID til innlogget bruker. Dette håndteres eksternt av loginController.

Ting som må oppdateres ved post  
- post  
- postedby  

I tillegg til en av disse  
- reply  
- followup  
- Thread  
	
Og en av disse  
- replyInFollowup  
- followupInThread  
- ThreadInFolder  


Dersom posten er en new thread så trenger vi input folder og course.

Dersom posten er en reply så skal post id som "replies til" være med.   
	Inne i denne sjekker vi så om vi får en match enten i Reply eller FollowUp.  
	fortsetter så tilsvarende der vi har fått en match
