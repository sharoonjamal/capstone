package learn.aaron.closet.shop.data;


import learn.aaron.closet.shop.models.AppUser;

public interface AppUserRepository {
    AppUser findByUsername(String username);

    AppUser add(AppUser appUser);
}
