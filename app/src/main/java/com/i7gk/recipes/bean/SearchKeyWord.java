package com.i7gk.recipes.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 64886 on 2017/2/24 0024.
 */

public class SearchKeyWord extends DataSupport {


    private String msg;
    private ResultBean result;
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class ResultBean {


        private int curPage;
        private int total;
        private List<ListBean> list;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {


            private String ctgTitles;
            private String menuId;
            private String name;
            private RecipeBean recipe;
            private String thumbnail;
            private List<String> ctgIds;

            public String getCtgTitles() {
                return ctgTitles;
            }

            public void setCtgTitles(String ctgTitles) {
                this.ctgTitles = ctgTitles;
            }

            public String getMenuId() {
                return menuId;
            }

            public void setMenuId(String menuId) {
                this.menuId = menuId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public RecipeBean getRecipe() {
                return recipe;
            }

            public void setRecipe(RecipeBean recipe) {
                this.recipe = recipe;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public List<String> getCtgIds() {
                return ctgIds;
            }

            public void setCtgIds(List<String> ctgIds) {
                this.ctgIds = ctgIds;
            }

            public static class RecipeBean {


                private String img;
                private String ingredients;
                private String method;
                private String sumary;
                private String title;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getIngredients() {
                    return ingredients;
                }

                public void setIngredients(String ingredients) {
                    this.ingredients = ingredients;
                }

                public String getMethod() {
                    return method;
                }

                public void setMethod(String method) {
                    this.method = method;
                }

                public String getSumary() {
                    return sumary;
                }

                public void setSumary(String sumary) {
                    this.sumary = sumary;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
