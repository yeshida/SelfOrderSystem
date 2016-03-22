package com.centerm.selforder.bean;

/**
 * Created by linwanliang on 2016/3/14.
 * 已点菜品实体类
 */
public class OrderedDish extends Dish {

    private int counts;
    private double amounts;
    private String selectedTaste;


    public OrderedDish(Dish dish, String selectedTaste, int counts) {
        super(dish.getId(), dish.getTypeId(), dish.getDetail(), dish.getName(),
                dish.getPicUrl(), dish.getPrice(), dish.getTastes());
        this.counts = counts;
        this.amounts = dish.getPrice() * counts;
        this.selectedTaste = selectedTaste;
    }

    public double getAmounts() {
        return amounts;
    }

    public void setAmounts(double amounts) {
        this.amounts = amounts;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getSelectedTaste() {
        return selectedTaste;
    }

    public void setSelectedTaste(String selectedTaste) {
        this.selectedTaste = selectedTaste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedDish that = (OrderedDish) o;

        if (!getId().equals(that.getId())) return false;
        if (getSelectedTaste() == null && that.getSelectedTaste() == null) return true;
        return getSelectedTaste().equals(that.getSelectedTaste());
    }

}
