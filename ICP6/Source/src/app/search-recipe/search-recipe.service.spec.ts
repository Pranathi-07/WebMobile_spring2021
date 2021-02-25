import { TestBed } from '@angular/core/testing';

import { SearchRecipeService } from './search-recipe.service';

describe('SearchRecipeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SearchRecipeService = TestBed.get(SearchRecipeService);
    expect(service).toBeTruthy();
  });
});
