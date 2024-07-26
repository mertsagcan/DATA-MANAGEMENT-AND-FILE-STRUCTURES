package ceng.ceng351.foodrecdb;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class FOODRECDB implements IFOODRECDB{

    private static String user = "e2310449";
    private static String password = "SGF6svgEOfcmbVrR";
    private static String host = "momcorp.ceng.metu.edu.tr";
    private static String database = "db2310449";
    private static int port = 8080;

    private Connection con;
    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con =  DriverManager.getConnection(url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createTables() {
        int result;
        int numOfTablesCreated = 0;

        String queryCreateMenuItemsTable = "create table if not exists MenuItems (" +
                                                                        "itemID int not null," +
                                                                        "itemName varchar(40)," +
                                                                        "cuisine varchar(20)," +
                                                                        "price int," +
                                                                        "primary key (itemID))";

        try {
            Statement statement = this.con.createStatement();

            //Player Table
            result = statement.executeUpdate(queryCreateMenuItemsTable);
            System.out.println(result);
            numOfTablesCreated++;

            //close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryCreateIngredientsTable = "create table if not exists Ingredients (" +
                                                                        "ingredientID int not null," +
                                                                        "ingredientName varchar(40)," +
                                                                        "primary key (ingredientID) )";

        try {
            Statement statement = this.con.createStatement();

            //Player Table
            result = statement.executeUpdate(queryCreateIngredientsTable);
            System.out.println(result);
            numOfTablesCreated++;

            //close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryCreateIncludesTable = "create table if not exists Includes (" +
                                                                        "itemID int not null," +
                                                                        "ingredientID int not null," +
                                                                        "primary key (itemID, ingredientID)," +
                                                                        "foreign key (itemID) references MenuItems(itemID) on delete cascade on update cascade," +
                                                                        "foreign key  (ingredientID) references Ingredients(ingredientID) on delete cascade on update cascade )";

        try {
            Statement statement = this.con.createStatement();

            //Player Table
            result = statement.executeUpdate(queryCreateIncludesTable);
            System.out.println(result);
            numOfTablesCreated++;

            //close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryCreateRatingsTable = "create table if not exists Ratings (" +
                                                                        "ratingID int not null," +
                                                                        "itemID int not null," +
                                                                        "rating int," +
                                                                        "ratingDate date," +
                                                                        "primary key (ratingID)," +
                                                                        "foreign key (itemID) references MenuItems(itemID) on delete cascade on update cascade )";

        try {
            Statement statement = this.con.createStatement();

            //Player Table
            result = statement.executeUpdate(queryCreateRatingsTable);
            System.out.println(result);
            numOfTablesCreated++;

            //close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryCreateDietaryCategoriesTable = "create table if not exists DietaryCategories (" +
                                                                                        "ingredientID int not null," +
                                                                                        "dietaryCategory varchar(20) not null," +
                                                                                        "primary key (ingredientID, dietaryCategory)," +
                                                                                        "foreign key (ingredientID) references Ingredients(ingredientID) on delete cascade on update cascade )";

        try {
            Statement statement = this.con.createStatement();

            //Player Table
            result = statement.executeUpdate(queryCreateDietaryCategoriesTable);
            System.out.println(result);
            numOfTablesCreated++;

            //close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numOfTablesCreated;
    }

    @Override
    public int dropTables() {
        int result;
        int numOfDroppedTables = 0;

        String dropDietaryCategoriesTable = "drop table if exists DietaryCategories";

        try {
            Statement statement = this.con.createStatement();

            //Team Player
            result = statement.executeUpdate(dropDietaryCategoriesTable);
            numOfDroppedTables++;
            System.out.println(result);

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropRatingsTable = "drop table if exists Ratings";

        try {
            Statement statement = this.con.createStatement();

            //Team Player
            result = statement.executeUpdate(dropRatingsTable);
            numOfDroppedTables++;
            System.out.println(result);

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropIncludesTable = "drop table if exists Includes";

        try {
            Statement statement = this.con.createStatement();

            //Team Player
            result = statement.executeUpdate(dropIncludesTable);
            numOfDroppedTables++;
            System.out.println(result);

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropIngredientsTable = "drop table if exists Ingredients";

        try {
            Statement statement = this.con.createStatement();

            //Team Player
            result = statement.executeUpdate(dropIngredientsTable);
            numOfDroppedTables++;
            System.out.println(result);

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropMenuItemsTable = "drop table if exists MenuItems";

        try {
            Statement statement = this.con.createStatement();

            //Team Player
            result = statement.executeUpdate(dropMenuItemsTable);
            numOfDroppedTables++;
            System.out.println(result);

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return numOfDroppedTables;
    }

    @Override
    public int insertMenuItems(MenuItem[] items) {
        int result ;
        int inserted = 0;
        int sizeOfList = items.length;

        for(int i = 0; i < sizeOfList; i++){
            String query = "insert into MenuItems values ('" +
                    items[i].getItemID()+ "','" +
                    items[i].getItemName() + "','" +
                    items[i].getCuisine() + "','" +
                    items[i].getPrice() + "')";

            try {
                Statement st = this.con.createStatement();
                result = st.executeUpdate(query);
                System.out.println(result);
                inserted++;

                //Close
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inserted;
    }

    @Override
    public int insertIngredients(Ingredient[] ingredients) {
        int result ;
        int inserted = 0;
        int sizeOfList = ingredients.length;

        for(int i = 0; i < sizeOfList; i++){
            String query = "insert into Ingredients values ('" +
                    ingredients[i].getIngredientID() + "','" +
                    ingredients[i].getIngredientName() + "')";

            try {
                Statement st = this.con.createStatement();
                result = st.executeUpdate(query);
                System.out.println(result);
                inserted++;

                //Close
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inserted;
    }

    @Override
    public int insertIncludes(Includes[] includes) {
        int result ;
        int inserted = 0;
        int sizeOfList = includes.length;

        for(int i = 0; i < sizeOfList; i++){
            String query = "insert into Includes values ('" +
                    includes[i].getItemID() + "','" +
                    includes[i].getIngredientID() + "')";

            try {
                Statement st = this.con.createStatement();
                result = st.executeUpdate(query);
                System.out.println(result);
                inserted++;

                //Close
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inserted;
    }

    @Override
    public int insertDietaryCategories(DietaryCategory[] categories) {
        int result ;
        int inserted = 0;
        int sizeOfList = categories.length;

        for(int i = 0; i < sizeOfList; i++){
            String query = "insert into DietaryCategories values ('" +
                    categories[i].getIngredientID() + "','" +
                    categories[i].getDietaryCategory() + "')";

            try {
                Statement st = this.con.createStatement();
                result = st.executeUpdate(query);
                System.out.println(result);
                inserted++;

                //Close
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inserted;
    }

    @Override
    public int insertRatings(Rating[] ratings) {
        int result ;
        int inserted = 0;
        int sizeOfList = ratings.length;

        for(int i = 0; i < sizeOfList; i++){
            String query = "insert into Ratings values ('" +
                    ratings[i].getRatingID() + "','" +
                    ratings[i].getItemID() + "','" +
                    ratings[i].getRating() + "','" +
                    ratings[i].getRatingDate() + "')";

            try {
                Statement st = this.con.createStatement();
                result = st.executeUpdate(query);
                System.out.println(result);
                inserted++;

                //Close
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inserted;
    }

    @Override
    public MenuItem[] getMenuItemsWithGivenIngredient(String name) {
        ResultSet result;
        ArrayList<MenuItem> includedMenuItems = new ArrayList<>();

        String query = "Select distinct M.itemID, M.itemName, M.cuisine, M.price " +
                        "from MenuItems M, Includes I, Ingredients X " +
                        "where I.ingredientID = X.ingredientID AND " +
                        "I.itemID = M.itemID AND X.ingredientName = ?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(query);
            stmt.setString(1, name);
            result = stmt.executeQuery();

            while (result.next()) {

                int itemID = result.getInt("itemID");
                String itemName = result.getString("itemName");
                String cuisine = result.getString("cuisine");
                int price= result.getInt("price");

                MenuItem resMenuItem = new MenuItem(itemID, itemName, cuisine, price);
                includedMenuItems.add(resMenuItem);
            }
            //Close
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MenuItem[] temp = new MenuItem[includedMenuItems.size()];

        return includedMenuItems.toArray(temp);
    }

    @Override
    public MenuItem[] getMenuItemsWithoutAnyIngredient() {
        ResultSet result;
        ArrayList<MenuItem> includedMenuItems = new ArrayList<>();

        String query = "select itemID, itemName, cuisine, price \n" +
                        "from MenuItems \n" +
                        "where not exists" +
                        "(select itemID from Includes \n" +
                        "where \n" +
                        "MenuItems.itemID = Includes.itemID)";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);

            while (result.next()) {
                int itemID = result.getInt("itemID");
                String itemName = result.getString("itemName");
                String cuisine = result.getString("cuisine");
                int price = result.getInt("price");

               MenuItem resMenuItem = new MenuItem(itemID, itemName, cuisine, price);
                includedMenuItems.add(resMenuItem);
            }

            //Close
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MenuItem[] temp = new MenuItem[includedMenuItems.size()];

        return includedMenuItems.toArray(temp);
    }

    @Override
    public Ingredient[] getNotIncludedIngredients() {
        ResultSet result;
        ArrayList<Ingredient> notIncludedIngredients = new ArrayList<>();

        String query = "select ingredientID, ingredientName \n" +
                "from Ingredients \n" +
                "where not exists \n" +
                "(select ingredientID from Includes \n" +
                "where \n" +
                "Ingredients.ingredientID = Includes.ingredientID)";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);

            while (result.next()) {
                int ingredientID = result.getInt("ingredientID");
                String ingredientName = result.getString("ingredientName");

                Ingredient resIngredient = new Ingredient(ingredientID, ingredientName);
                notIncludedIngredients.add(resIngredient);
            }

            //Close
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Ingredient[] temp = new Ingredient[notIncludedIngredients.size()];

        return notIncludedIngredients.toArray(temp);
    }

    @Override
    public MenuItem getMenuItemWithMostIngredients() {
        ResultSet result;
        MenuItem finalItem = new MenuItem(0, "", "", 0);

        String query = "select M.itemID, M.itemName, M.cuisine, M.price \n" +
                        "from MenuItems M, Includes I \n" +
                        "where M.itemID = I.itemID \n" +
                        "group by M.itemID \n" +
                        "having count(I.ingredientID) >= ALL(select count(I1.ingredientID) from Includes I1 \n" +
                                                            "group by I1.itemID ) \n";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);
            result.next();

            int itemID = result.getInt("itemID");
            String itemName = result.getString("itemName");
            String cuisine = result.getString("cuisine");
            int price = result.getInt("price");

            MenuItem temp = new MenuItem(itemID, itemName, cuisine, price);

            finalItem = temp;

            //Close
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalItem;
    }

    @Override
    public QueryResult.MenuItemAverageRatingResult[] getMenuItemsWithAvgRatings() {
        ResultSet result;
        ArrayList<QueryResult.MenuItemAverageRatingResult> avgRatingList = new ArrayList<>();

        String query = "select M.itemId, M.itemName, avg(R.rating) as avgRating \n" +
                        "from MenuItems M natural left outer join Ratings R \n" +
                        "group by M.itemID \n" +
                        "order by avgRating desc";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);

            while (result.next()) {
                String itemID = result.getString("itemID");
                String itemName = result.getString("itemName");
                String avgRating = result.getString("avgRating");

                QueryResult.MenuItemAverageRatingResult resAvgRating = new QueryResult.MenuItemAverageRatingResult(itemID, itemName, avgRating);
                avgRatingList.add(resAvgRating);
            }

            //Close
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        QueryResult.MenuItemAverageRatingResult[] temp = new QueryResult.MenuItemAverageRatingResult[avgRatingList.size()];

        return avgRatingList.toArray(temp);
    }

    @Override
    public MenuItem[] getMenuItemsForDietaryCategory(String category) {
        ResultSet result;
        ArrayList<MenuItem> finalMenuItems = new ArrayList<>();

        String query = "select distinct M.itemID, M.itemName, M.cuisine, M.price \n" +
                        "from MenuItems M, Includes I1 \n" +
                        "where I1.itemID = M.itemID and not exists( \n" +
                                            "select I.ingredientID \n" +
                                            "from Includes I \n" +
                                            "where I.itemID = M.itemID and I.ingredientID \n" +
                                            "not in \n" +
                                            "(select D.ingredientID \n"+
                                            "from DietaryCategories D, Includes I1 \n" +
                                            "where D.ingredientID = I1.ingredientID and M.itemID = I1.itemID and \n" +
                                            "D.dietaryCategory = ?))";

        try {
            PreparedStatement stmt = this.con.prepareStatement(query);
            stmt.setString(1, category);
            result = stmt.executeQuery();

            while (result.next()) {

                int itemID = result.getInt("itemID");
                String itemName = result.getString("itemName");
                String cuisine = result.getString("cuisine");
                int price= result.getInt("price");

                MenuItem resMenuItem = new MenuItem(itemID, itemName, cuisine, price);
                finalMenuItems.add(resMenuItem);
            }
            //Close
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MenuItem[] temp = new MenuItem[finalMenuItems.size()];

        return finalMenuItems.toArray(temp);
    }

    @Override
    public Ingredient getMostUsedIngredient() {
        ResultSet result;
        Ingredient finalIngredient = new Ingredient(0, "");

        String query = "select I.ingredientID, I.ingredientName \n" +
                        "from Ingredients I, Includes S \n" +
                        "where I.ingredientID = S.ingredientID \n" +
                        "group by I.ingredientID \n" +
                        "having count(S.itemID) >= ALL(select count(itemID) from Ingredients I1, Includes S1 \n" +
                                                        "where I1.ingredientID = S1.ingredientID \n" +
                                                        "group by I1.ingredientID )";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);
            result.next();

            int ingredientID = result.getInt("ingredientID");
            String ingredientName = result.getString("ingredientName");

            Ingredient temp = new Ingredient(ingredientID, ingredientName);

            finalIngredient = temp;

            //Close
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalIngredient;
    }

    @Override
    public QueryResult.CuisineWithAverageResult[] getCuisinesWithAvgRating() {
        ResultSet result;
        ArrayList<QueryResult.CuisineWithAverageResult> finalAvgRating = new ArrayList<>();

        String query = "select M.cuisine, avg(R.rating) avgRating \n"+
                        "from MenuItems M natural left outer join Ratings R \n" +
                        "group by M.cuisine \n" +
                        "order by avgRating desc";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);

            while (result.next()) {
                String cuisine = result.getString("cuisine");
                String avgRating = result.getString("avgRating");

                QueryResult.CuisineWithAverageResult resAvgRating = new QueryResult.CuisineWithAverageResult(cuisine, avgRating);
                finalAvgRating.add(resAvgRating);
            }

            //Close
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        QueryResult.CuisineWithAverageResult[] temp = new QueryResult.CuisineWithAverageResult[finalAvgRating.size()];

        return finalAvgRating.toArray(temp);
    }

    @Override
    public QueryResult.CuisineWithAverageResult[] getCuisinesWithAvgIngredientCount() {
        ResultSet result;
        ArrayList<QueryResult.CuisineWithAverageResult> finalAvgIngredientCount = new ArrayList<>();

        String query = "select Temp.cuisine, avg(Temp.numOfIngredients) as average \n" +
                        "from (select M.cuisine as cuisine, count(S.ingredientID) as numOfIngredients \n" +
                                "from MenuItems M natural left outer join Includes S \n" +
                                "group by M.itemID ) as Temp \n" +
                        "group by Temp.cuisine \n" +
                        "order by AVG(Temp.numOfIngredients) desc";

        try {
            Statement st = this.con.createStatement();
            result = st.executeQuery(query);

            while (result.next()) {
                String cuisine = result.getString("cuisine");
                String average = result.getString("average");

                QueryResult.CuisineWithAverageResult resAvgRating = new QueryResult.CuisineWithAverageResult(cuisine, average);
                finalAvgIngredientCount.add(resAvgRating);
            }

            //Close
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        QueryResult.CuisineWithAverageResult[] temp = new QueryResult.CuisineWithAverageResult[finalAvgIngredientCount.size()];

        return finalAvgIngredientCount.toArray(temp);
    }

    @Override
    public int increasePrice(String ingredientName, String increaseAmount) {
        int result = 0;
        int effectedRows = 0;

        String query = "update MenuItems \n" +
                        "set price = price + ? \n" +
                        "where itemID in (select S.itemID from Ingredients I, Includes S \n" +
                                            "where S.ingredientID = I.ingredientID and I.ingredientName = ?)";

        try {
            PreparedStatement stmt = this.con.prepareStatement(query);
            stmt.setString(1, increaseAmount);
            stmt.setString(2, ingredientName);
            result = stmt.executeUpdate();

            //Close
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Rating[] deleteOlderRatings(String date) {
        ResultSet result;
        ArrayList<Rating> deletedRows = new ArrayList<>();

        String query = "select R.ratingID, R.itemID, R.rating, R.ratingDate from Ratings R where R.ratingDate < ?";

        String query1 = "delete from Ratings where Ratings.ratingDate < ?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(query);
            stmt.setString(1, date);
            result = stmt.executeQuery();

            while (result.next()) {
                int ratingID = result.getInt("ratingId");
                int itemID = result.getInt("itemID");
                int rating = result.getInt("rating");
                String ratingDate = result.getString("ratingDate");

                Rating resRating = new Rating(ratingID, itemID, rating, ratingDate);
                deletedRows.add(resRating);
            }

            try {

                PreparedStatement stmt2=this.con.prepareStatement(query1);
                stmt2.setString(1,date);
                stmt2.executeUpdate();
                //close
                stmt2.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Close
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Rating[] temp = new Rating[deletedRows.size()];

        return deletedRows.toArray(temp);
    }
}
