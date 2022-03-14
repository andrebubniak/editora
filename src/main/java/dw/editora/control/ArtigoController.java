package dw.editora.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dw.editora.model.Artigo;
import dw.editora.repository.ArtigoRepository;

@Controller
public class ArtigoController 
{

    @Autowired
    ArtigoRepository artigoRep;
    

    // pega todos os artigos do banco e coloca na tabela em /artigos
    @GetMapping("/artigos")
    public ModelAndView getAllArtigos(@RequestParam(required = false) String titulo)
    {
        ModelAndView mav = new ModelAndView("ArtigosList");
        List<Artigo> la = artigoRep.findAll();
        mav.addObject("artigosList", la);
        return mav;
    }


    //vai pro formulario de criação de artigo
    @GetMapping("/artigos/create")
    public ModelAndView createArtigo()
    {
        ModelAndView mav = new ModelAndView("artigoForm");
        Artigo artigo = new Artigo();
        artigo.setPublicado(true);
        mav.addObject("artigo", artigo);
        return mav;
    }



    //vai pro formulario de update do artigo (o mesmo de criação, só que com a informação do id)
    @GetMapping("/artigos/update/")
    public ModelAndView updateArtigo(@RequestParam(value = "id", required = true) Long id)
    {
        ModelAndView mav = new ModelAndView("artigoForm");
        Artigo artigo = artigoRep.findById(id).get();
        mav.addObject("artigo", artigo);
        return mav;
    }


    //salva um artigo (se tiver id ele da update, senão cria outro) e volta pra tabela
    @PostMapping("/artigos/salvarArtigo")
    public String salvarArtigo(@ModelAttribute Artigo artigo)
    {
        artigoRep.save(artigo);
        return "redirect:/artigos";
    }

    //deleta um artigo
    @GetMapping("/artigos/deletarArtigo/")
    public String deleteArtigo(@RequestParam(value = "id", required = true) Long id)
    {
        artigoRep.deleteById(id);
        return "redirect:/artigos";
    }

    //deleta todos os artigos
    @GetMapping("/artigos/deleteAll")
    public String deleteAll()
    {
        artigoRep.deleteAll();
        return "redirect:/artigos";
    }
}