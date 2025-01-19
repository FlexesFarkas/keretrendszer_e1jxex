package com.keretrendszer_e1jxex.keretrendszer_e1jxex.controller;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.GameDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.PurchaseDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Role;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/devpage")
public class GameController {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PurchaseDAO purchaseDAO;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";





    @GetMapping
    public String getGames(@AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser, Model model) {
        Optional<User> optionalUser = userDAO.findByUsername(securityUser.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            boolean isAdmin = user.getRoles().stream()
                    .map(Role::getName)
                    .anyMatch(role -> role.equals("ADMIN"));

            List<Game> games;
            if (isAdmin) {
                games = gameDAO.findAll();
            } else {
                games = gameDAO.findByDeveloperId(user.getId());
            }

            model.addAttribute("games", games);
            return "devpage";
        }

        return "redirect:/plain-login";
    }


    @GetMapping("/new")
    public String showNewGameForm(@AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser, Model model) {
        model.addAttribute("game", new Game());
        return "new-game";
    }

    @PostMapping("/new")
    public String createGame(@ModelAttribute Game game,
                             @RequestParam("video") MultipartFile video,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser,
                             RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userDAO.findByUsername(securityUser.getUsername());
        if (securityUser != null) {
            game.setDeveloper(optionalUser.get());
        } else {
            redirectAttributes.addFlashAttribute("error", "Be kell jelentkezned hogy játékot tudj csinálni..");
            return "redirect:/plain-login";
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean hasRequiredRole = user.getRoles().stream()
                    .map(Role::getName)
                    .anyMatch(role -> role.equals("ADMIN") || role.equals("DEVELOPER"));
            if (hasRequiredRole) {
                String videoPath = saveVideo(video);
                game.setImagePath(videoPath);

                gameDAO.save(game);
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Csak admin vagy developer csinálhat játékot.");
            return "redirect:/plain-login";
        }

        return "redirect:/devpage";
    }



    @GetMapping("/edit/{id}")
    public String showEditGameForm(@PathVariable("id") int id,
                                   @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser,
                                   Model model, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userDAO.findByUsername(securityUser.getUsername());
        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Be kell jelentkezned, hogy szerkeszthesd a játékot.");
            return "redirect:/plain-login";
        }

        User loggedUser = optionalUser.get();
        Game game = gameDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid game ID"));

        if (!game.getDeveloper().getId().equals(loggedUser.getId())) {
            redirectAttributes.addFlashAttribute("error", "Nincs jogosultságod a játék szerkesztéséhez.");
            return "redirect:/devpage";
        }

        model.addAttribute("game", game);
        return "edit-game";
    }


    @PostMapping("/edit/{id}")
    public String updateGame(@PathVariable("id") int id, @ModelAttribute Game game,
                             @RequestParam("video") MultipartFile video,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser) {
        if (securityUser != null) {
            User developer = userDAO.findByUsername(securityUser.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            game.setDeveloper(developer);
        }
        if (!video.isEmpty()) {
            String videoPath = saveVideo(video);
            game.setImagePath(videoPath);
        }

        gameDAO.update(game);
        return "redirect:/devpage";
    }


    @GetMapping("/delete/{id}")
    public String deleteGame(@PathVariable("id") int id, @AuthenticationPrincipal User user) {
        gameDAO.delete(id);
        return "redirect:/devpage";
    }



    private String saveImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }
        try {
            String fileName = image.getOriginalFilename();
            Path targetLocation = new File(UPLOAD_DIR + fileName).toPath();
            Files.createDirectories(targetLocation.getParent());
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "images/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Kép mentése sikertelen. :(", e);
        }
    }


    private static final String UPLOAD_VIDEO_DIR = "src/main/resources/static/videos/";

    private String saveVideo(MultipartFile video) {
        if (video.isEmpty()) {
            return null;
        }
        try {
            String fileName = video.getOriginalFilename();
            Path targetLocation = new File(UPLOAD_VIDEO_DIR + fileName).toPath();
            Files.createDirectories(targetLocation.getParent());
            Files.copy(video.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "videos/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Videó mentése sikertelen. :(", e);
        }
    }




}
