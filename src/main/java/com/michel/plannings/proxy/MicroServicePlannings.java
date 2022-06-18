package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.plannings.models.FicheAux;
import com.michel.plannings.models.Login;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.models.forms.FormFiche;


//@FeignClient(name = "plannings", url = "http://46.105.35.7:8103")
@FeignClient(name = "plannings", url = "http://localhost:8103/")
public interface MicroServicePlannings extends ProxyGlobal{



	
	
	



	

	
	

	

	



	

	

	
	

	

	

	


}
