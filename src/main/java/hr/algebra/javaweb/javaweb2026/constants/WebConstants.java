package hr.algebra.javaweb.javaweb2026.constants;

public final class WebConstants {

    private WebConstants() {
    }

    public static final class Views {
        private Views() {
        }

        public static final String HOME = "home";
        public static final String PRODUCTS = "products";
        public static final String CART = "cart";
        public static final String CHECKOUT = "checkout";
        public static final String CHECKOUT_SUCCESS = "checkout-success";
        public static final String ORDERS = "orders";
        public static final String REGISTER = "register";

        public static final String ADMIN_PRODUCTS = "admin/products";
        public static final String ADMIN_PRODUCT_EDIT = "admin/product-edit";
        public static final String ADMIN_CATEGORIES = "admin/categories";
        public static final String ADMIN_CATEGORY_EDIT = "admin/category-edit";
        public static final String ADMIN_LOGIN_HISTORY = "admin/login-history";
        public static final String ADMIN_ORDERS = "admin/orders";
    }

    public static final class ModelAttributes {
        private ModelAttributes() {
        }

        public static final String PRODUCTS = "products";
        public static final String CATEGORIES = "categories";
        public static final String SELECTED_CATEGORY = "selectedCategory";
        public static final String CART_ITEMS = "cartItems";
        public static final String CART_TOTAL = "cartTotal";
        public static final String CHECKOUT_DTO = "checkoutDTO";
        public static final String PAYMENT_METHODS = "paymentMethods";
        public static final String ORDER = "order";
        public static final String ORDERS = "orders";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String SUCCESS_MESSAGE = "successMessage";
        public static final String PRODUCT_DTO = "productDTO";
        public static final String OLD_PRODUCT_NAME = "oldProductName";
        public static final String CATEGORY_DTO = "categoryDTO";
        public static final String OLD_CATEGORY_NAME = "oldCategoryName";
        public static final String LOGIN_HISTORY_RECORDS = "loginHistoryRecords";
        public static final String HISTORY_SEARCH_FORM = "historySearchForm";
        public static final String USER_REGISTRATION_DTO = "userRegistrationDTO";
        public static final String PAYPAL_CLIENT_ID = "paypalClientId";

    }

    public static final class SessionAttributes {
        private SessionAttributes() {
        }

        public static final String CART = "cart";
    }

    public static final class Redirects {
        private Redirects() {
        }

        public static final String CART = "redirect:/cart";
        public static final String CHECKOUT = "redirect:/checkout";
        public static final String PRODUCTS = "redirect:/products";
        public static final String ADMIN_PRODUCTS = "redirect:/admin/products";
        public static final String ADMIN_CATEGORIES = "redirect:/admin/categories";
        public static final String LOGIN = "redirect:/login";

    }

    public static final class Values {
        private Values() {
        }

        public static final String ALL = "All";
        public static final String SUCCESS = "success";
    }

    public static final class ErrorMessages {
        private ErrorMessages() {
        }

        public static final String PRODUCT = "Product not found: ";
    }


}