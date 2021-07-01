package com.sda.jz75_security_template.service;

import com.sda.jz75_security_template.model.Account;
import com.sda.jz75_security_template.model.IloscSkladnika;
import com.sda.jz75_security_template.model.Przepis;
import com.sda.jz75_security_template.model.Skladnik;
import com.sda.jz75_security_template.repository.IloscSkladnikaRepository;
import com.sda.jz75_security_template.repository.PrzepisRepository;
import com.sda.jz75_security_template.repository.SkladnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final SkladnikRepository skladnikRepository;
    private final IloscSkladnikaRepository iloscSkladnikaRepository;
    private final PrzepisRepository przepisRepository;

    public void addRecipe(String recipeName, Account account){

        przepisRepository.save(Przepis.builder()
                .name(recipeName)
                .creator(account)
                .build());
    }

    public void addIngredient(String ingredientName){
        // TODO: weryfikacja ze taki ingr. nie istnieje.

        skladnikRepository.save(Skladnik.builder().name(ingredientName).build());
    }

    public List<Przepis> getAllRecipies() {
        return przepisRepository.findAll();
    }

    public List<Skladnik> getAllIngredients() {
        return skladnikRepository.findAll();
    }

    public void addIngredientToRecipe(Long przepis_id, Long skladnik_id, double ilosc) {
        Optional<Skladnik> skladnikOptional = skladnikRepository.findById(skladnik_id);
        Optional<Przepis> przepisOptional = przepisRepository.findById(przepis_id);

        if(skladnikOptional.isPresent() && przepisOptional.isPresent()){
            IloscSkladnika iloscSkladnika = IloscSkladnika.builder()
                    .przepis(przepisOptional.get())
                    .skladnik(skladnikOptional.get())
                    .ilosc(ilosc)
                    .build();

            iloscSkladnikaRepository.save(iloscSkladnika);
        }
    }
}
