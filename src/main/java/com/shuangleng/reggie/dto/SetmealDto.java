package com.shuangleng.reggie.dto;
import com.shuangleng.reggie.entity.Setmeal;
import com.shuangleng.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
