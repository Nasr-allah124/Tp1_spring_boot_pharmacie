package tp1.tp1_exemple_cours.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tp1.tp1_exemple_cours.repository.*;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ProduitRepository produitRepo;
    private final CategorieRepository categorieRepo;
    private final FournisseurRepository fournisseurRepo;
    private final TagRepository tagRepo;

    @GetMapping("/")
    public String accueil() {
        return "redirect:/produits";
    }

    @GetMapping("/produits")
    public String produits(Model model) {
        model.addAttribute("produits", produitRepo.findAll());
        return "produits";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categorieRepo.findAll());
        return "categories";
    }

    @GetMapping("/fournisseurs")
    public String fournisseurs(Model model) {
        model.addAttribute("fournisseurs", fournisseurRepo.findAll());
        return "fournisseurs";
    }

    @GetMapping("/tags")
    public String tags(Model model) {
        model.addAttribute("tags", tagRepo.findAll());
        return "tags";
    }
}
